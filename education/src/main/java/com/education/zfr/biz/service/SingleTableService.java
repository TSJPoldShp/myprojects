package com.education.zfr.biz.service;

import com.education.zfr.biz.entity.CpnStaff;
import com.education.zfr.common.mvc.HttpResult;
import com.education.zfr.common.utils.ExcelFileUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * Created by zangfr on 2017/4/24.
 */
@Service
public class SingleTableService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SingleTableService.class);

    public HttpResult importExcel(HttpServletRequest request){
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        Map<String,MultipartFile> fileMap = multipartRequest.getFileMap();
        for(Map.Entry<String,MultipartFile> entity : fileMap.entrySet()){
            try{
                String fileName = entity.getValue().getOriginalFilename();
                HSSFWorkbook book = new HSSFWorkbook(entity.getValue().getInputStream());
                List<List<String>> list = ExcelFileUtil.importExcel(book);
                Map<String, List<CpnStaff>> saveMap = this.saveImportExcel(list, fileName);
            }catch (Exception e) {
                LOGGER.error("Exit func[importExcel()] throw exception",e);
            }
        }

        return null;
    }

    private Map<String,List<CpnStaff>> saveImportExcel(List<List<String>> list,String fileName){
        Map<String, List<CpnStaff>> returnMap = Maps.newHashMap();
        List<CpnStaff> unSaveDatas = Lists.newArrayList();
        List<CpnStaff> saveDatas = Lists.newArrayList();
        for(List<String> row : list){
            CpnStaff staffExcel = new CpnStaff();
            try{
                Long staffId = Long.parseLong(row.get(0));//员工编号
                Long departmentId = Long.parseLong(row.get(1));
                Long positionId = Long.parseLong(row.get(2));
                String staffName = row.get(3);
                staffExcel.setStaffId(staffId);
            }catch (Exception e){
                LOGGER.error("Exit func[saveImportExcel()] throw exception",e);
            }
        }
        return null;
    }
}
