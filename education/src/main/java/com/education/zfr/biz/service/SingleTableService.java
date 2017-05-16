package com.education.zfr.biz.service;

import com.education.zfr.biz.dao.DepartmentDao;
import com.education.zfr.biz.dao.PositionDao;
import com.education.zfr.biz.dao.StaffDao;
import com.education.zfr.biz.entity.CpnDepartmentStaffRel;
import com.education.zfr.biz.entity.CpnPositionStaffRel;
import com.education.zfr.biz.entity.CpnStaff;
import com.education.zfr.common.mvc.HttpResult;
import com.education.zfr.common.utils.Constants;
import com.education.zfr.common.utils.ExcelFileUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by zangfr on 2017/4/24.
 */
@Service
public class SingleTableService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SingleTableService.class);

    @Autowired
    private StaffService staffService;

    @Autowired
    private StaffDao staffDao;

    @Autowired
    private DepartmentDao departmentDao;

    @Autowired
    private PositionDao positionDao;

    @Autowired
    private PositionStaffRelService positionStaffRelService;

    @Autowired
    private DepartmentStaffRelService departmentStaffRelService;

    public HttpResult importExcel(HttpServletRequest request){
        HttpResult result = new HttpResult();
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        Map<String,MultipartFile> fileMap = multipartRequest.getFileMap();
        List<CpnStaff> saved = Lists.newArrayList();
        List<CpnStaff> unSave = Lists.newArrayList();
        for(Map.Entry<String,MultipartFile> entity : fileMap.entrySet()){
            try{
                HSSFWorkbook book = new HSSFWorkbook(entity.getValue().getInputStream());
                List<List<String>> list = ExcelFileUtil.importExcel(book);
                Map<String, List<CpnStaff>> saveMap = this.saveImportExcel(list);
                saved.addAll(saveMap.get("saved"));
                unSave.addAll(saveMap.get("unSave"));
            }catch (Exception e) {
                LOGGER.error("Exit func[importExcel()] throw exception",e);
                result.setResult(Constants.HTTP_SYSTEM_ERROR,"数据不正确");
            }
        }
        int save = saved.size();
        int unsave = unSave.size();
        int all = save + unsave;
        if(CollectionUtils.isEmpty(unSave)){
            result.setResult(Constants.HTTP_SYSTEM_OK,"本次导入成功"+save+"条，失败"+unsave+"条，共"+all+"条.");
        }else{
            StringBuilder content = new StringBuilder();
            for (CpnStaff staff : unSave) {
                content.append("第" + staff.getNumber() + "条数据导入失败,原因[" + staff.getDesc() + "]");
            }
            result.setResult(Constants.HTTP_SYSTEM_WARN, "本次导入成功" + save + "条," + "失败" + unsave + "条," + "合计" + all + "条." + content);
        }
        return result;
    }

    private Map<String,List<CpnStaff>> saveImportExcel(List<List<String>> list){
        Map<String, List<CpnStaff>> returnMap = Maps.newHashMap();
        List<CpnStaff> unSaveDatas = Lists.newArrayList();
        List<CpnStaff> saveDatas = Lists.newArrayList();
        int i = 1;
        for(List<String> row : list){
            CpnStaff staffExcel = new CpnStaff();
            staffExcel.setNumber(i++);
            try{
                Long departmentId = Long.parseLong(row.get(0));//部门编号
                Long positionId = Long.parseLong(row.get(1));//职位编号
                String staffName = row.get(2);//员工姓名
                String staffGender = row.get(3);//员工性别
                String staffNumber = row.get(4);//身份证号
                String loginName = row.get(5);//登录名
                String loginPwd = row.get(6);//登录密码

                staffExcel.setDepartmentId(departmentId);
                staffExcel.setPositionId(positionId);
                staffExcel.setStaffName(staffName);
                staffExcel.setStaffGender(staffGender);
                staffExcel.setStaffNumber(staffNumber);
                staffExcel.setLoginName(loginName);
                staffExcel.setLoginPwd(loginPwd);

                if(checkStaff(staffExcel)){
                    saveDatas.add(staffExcel);
                }
                else{
                    unSaveDatas.add(staffExcel);
                }
            }catch (Exception e){
                LOGGER.error("Exit func[saveImportExcel()] throw exception",e);
                staffExcel.setDesc("数据不正确");
                unSaveDatas.add(staffExcel);
            }
        }
        if (!CollectionUtils.isEmpty(saveDatas)) {
            SaveDatas(saveDatas);
        }
        returnMap.put("saved",saveDatas);
        returnMap.put("unSave",unSaveDatas);
        return returnMap;
    }

    public Boolean checkStaff(CpnStaff staff){

        if(StringUtils.isEmpty(staff.getDepartmentId())){
            LOGGER.info("Exit func[checkStaff()],departmentId is empty.");
            staff.setDesc("部门编号为空");
            return false;
        }
        if(StringUtils.isEmpty(staff.getPositionId())){
            LOGGER.info("Exit func[checkStaff()],positionId is empty.");
            staff.setDesc("职位编号为空");
            return false;
        }
        if(StringUtils.isEmpty(staff.getStaffName())){
            LOGGER.info("Exit func[checkStaff()],staffName is empty.");
            staff.setDesc("员工姓名为空");
            return false;
        }
        if(StringUtils.isEmpty(staff.getStaffGender())){
            LOGGER.info("Exit func[checkStaff()],staffGender is empty.");
            staff.setDesc("员工性别为空");
            return false;
        }
        if(StringUtils.isEmpty(staff.getStaffNumber())){
            LOGGER.info("Exit func[checkStaff()],staffNumber is empty.");
            staff.setDesc("身份证号为空");
            return false;
        }
        if(StringUtils.isEmpty(staff.getLoginName())){
            LOGGER.info("Exit func[checkStaff()],loginName is empty.");
            staff.setDesc("登录名为空");
            return false;
        }
        if(StringUtils.isEmpty(staff.getLoginPwd())){
            LOGGER.info("Exit func[checkStaff()],loginPwd is empty.");
            staff.setDesc("登录密码为空");
            return false;
        }
        if(null != staffService.findByStaffNumber(staff.getStaffNumber())){
            LOGGER.info("Exit func[checkStaff()],staff is already exist.");
            staff.setDesc("员工已存在");
            return false;
        }
        if(null == departmentDao.findOne(staff.getDepartmentId())){
            LOGGER.info("Exit func[checkStaff()],department is not exist.");
            staff.setDesc("部门不存在");
            return false;
        }
        if(null == positionDao.findOne(staff.getPositionId())){
            LOGGER.info("Exit func[checkStaff()],position is not exist.");
            staff.setDesc("职位不存在");
            return false;
        }
        return true;
    }

    public void SaveDatas(List<CpnStaff> saveDatas){
        for (CpnStaff staff : saveDatas) {
            staffService.saveStaff(staff);
            CpnDepartmentStaffRel departmentStaffRel = new CpnDepartmentStaffRel();
            CpnPositionStaffRel positionStaffRel = new CpnPositionStaffRel();
            departmentStaffRel.setDepartmentId(staffService.findByStaffNumber(staff.getStaffNumber()).getDepartmentId());
            departmentStaffRel.setStaffId(staff.getStaffId());
            positionStaffRel.setPositionId(staffService.findByStaffNumber(staff.getStaffNumber()).getPositionId());
            positionStaffRel.setStaffId(staff.getStaffId());
            if (null != departmentStaffRel) {
                departmentStaffRelService.saveDepartmentStaffRel(departmentStaffRel);
            }
            if (null != positionStaffRel) {
                positionStaffRelService.savePositionStaffRel(positionStaffRel);
            }
        }
    }
}
