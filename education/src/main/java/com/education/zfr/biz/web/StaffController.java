package com.education.zfr.biz.web;

import com.education.zfr.biz.entity.CpnDepartmentStaffRel;
import com.education.zfr.biz.entity.CpnPositionStaffRel;
import com.education.zfr.biz.entity.CpnStaff;
import com.education.zfr.biz.service.DepartmentStaffRelService;
import com.education.zfr.biz.service.PositionStaffRelService;
import com.education.zfr.biz.service.StaffService;
import com.education.zfr.common.mvc.HttpResult;
import com.education.zfr.common.mvc.Servlets;
import com.education.zfr.common.utils.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import java.util.Map;

/**
 * Created by zangfr on 2017/4/19.
 */
@Controller
@RequestMapping("/staff")
public class StaffController {

    private static final Logger LOGGER = LoggerFactory.getLogger(StaffController.class);

    @Autowired
    private StaffService staffService;

    @Autowired
    private DepartmentStaffRelService departmentStaffRelService;

    @Autowired
    private PositionStaffRelService positionStaffRelService;

    /**
     * 进入员工页，获取员工列表
     *
     * @param model
     * @return
     */
    @RequestMapping("staffList")
    public String getStaffList(@RequestParam(value = "pageNum", defaultValue = "1") int pageNumber,
                               @RequestParam(value = "numPerPage", defaultValue = "20") int pageSize,
                               Model model, ServletRequest request) {
        Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
        Page<CpnStaff> staffs = staffService.getStaffList(searchParams, pageNumber, pageSize);

        model.addAttribute("cpnStaffs", staffs);
        model.addAttribute("totalCount",staffs.getTotalElements());
        model.addAttribute("searchParams", Servlets.encodeParameterStringWithPrefix(searchParams, "search_"));
        model.addAttribute("pageNum", pageNumber);
        model.addAttribute("numPerPage", pageSize);
        return "biz/staff/staffList";
    }

    /**
     * 进入员工新增页面
     *
     * @return
     */
    @RequestMapping("toAddStaff")
    public String getStaffList() {
        return "biz/staff/addStaff";
    }

    /**
     * 新增员工
     *
     * @param staff
     * @return
     * @startuml start
     * if(find staff by staffNumber) then (not found)
     * :save staff;
     * :save staff department rel;
     * :save staff position rel;
     * else (found)
     * :return "staff is already existed";
     * endif
     * end
     * @enduml
     */
    @RequestMapping("/addStaff")
    @ResponseBody
    public HttpResult addStaff(CpnStaff staff) {
        HttpResult result = new HttpResult();
        CpnDepartmentStaffRel cpnDepartmentStaffRel = new CpnDepartmentStaffRel();
        CpnPositionStaffRel cpnPositionStaffRel = new CpnPositionStaffRel();
        try {
            if (null != staff) {
                if (null == staffService.findByStaffNumber(staff.getStaffNumber())) {
                    staffService.saveStaff(staff);
                    cpnDepartmentStaffRel.setStaffId(staff.getStaffId());
                    cpnDepartmentStaffRel.setDepartmentId(staff.getDepartmentId());
                    if (null == departmentStaffRelService.findByStaffIdAndDepartmentId(
                            cpnDepartmentStaffRel.getStaffId(), cpnDepartmentStaffRel.getDepartmentId())) {
                        departmentStaffRelService.saveDepartmentStaffRel(cpnDepartmentStaffRel);
                    }
                    cpnPositionStaffRel.setStaffId(staff.getStaffId());
                    cpnPositionStaffRel.setPositionId(staff.getPositionId());
                    if (null == positionStaffRelService.findByStaffIdAndPositionId(
                            cpnPositionStaffRel.getStaffId(), cpnPositionStaffRel.getPositionId())) {
                        positionStaffRelService.savePositionStaffRel(cpnPositionStaffRel);
                    }
                    result.setResult(Constants.HTTP_SYSTEM_OK, Constants.MESSAGE_OPERATION_OK);
                } else {
                    result.setResult(Constants.HTTP_SYSTEM_ERROR, "员工信息已存在");
                }
            }else{
                result.setResult(Constants.HTTP_SYSTEM_ERROR, "新增员工失败");
            }
        } catch (Exception e) {
            result.setResult(Constants.HTTP_SYSTEM_ERROR, "新增员工失败");
            LOGGER.error("Exit func[addStaff()] throw exception", e);
        }
        return result;
    }

    /**
     * 进入员工更新页
     *
     * @param staffId
     * @param model
     * @return
     */
    @RequestMapping("toUpdateStaff/{id}")
    public String toUpdateStaff(@PathVariable("id") Long staffId, Model model) {
        model.addAttribute("staff", staffService.findStaffByStaffId(staffId));
        return "biz/staff/staffInfo";
    }

    /**
     * 员工更新
     *
     * @param staff
     * @return
     */
    @RequestMapping("updateStaff")
    @ResponseBody
    public HttpResult updateStaff(CpnStaff staff) {
        HttpResult result = new HttpResult();
        CpnDepartmentStaffRel cpnDepartmentStaffRel = departmentStaffRelService.findByStaffId(staff.getStaffId());
        if(null != cpnDepartmentStaffRel) {
            cpnDepartmentStaffRel.setDepartmentId(staff.getDepartmentId());
        }
        CpnPositionStaffRel cpnPositionStaffRel = positionStaffRelService.findByStaffId(staff.getStaffId());
        if(null != cpnPositionStaffRel) {
            cpnPositionStaffRel.setPositionId(staff.getPositionId());
        }
        try {
            staffService.saveStaff(staff);
            departmentStaffRelService.saveDepartmentStaffRel(cpnDepartmentStaffRel);
            positionStaffRelService.savePositionStaffRel(cpnPositionStaffRel);
            result.setResult(Constants.HTTP_SYSTEM_OK, Constants.MESSAGE_OPERATION_OK);
        } catch (Exception e) {
            result.setResult(Constants.HTTP_SYSTEM_ERROR, "发生未知错误");
            LOGGER.error("Exit func[updateStaff()] throw exception", e);
        }
        return result;
    }

    /**
     * 员工删除
     *
     * @param staffId
     * @return
     */
    @RequestMapping("delete/{id}")
    @ResponseBody
    public HttpResult deleteStaff(@PathVariable("id") Long staffId) {
        HttpResult result = new HttpResult();
        Boolean deleted = staffService.deleteStaffById(staffId)
                &&departmentStaffRelService.deleteByStaffId(staffId)
                &&positionStaffRelService.deleteByStaffId(staffId) ? true : false;
        if (deleted) {
            result.setResult(Constants.HTTP_SYSTEM_OK, Constants.MESSAGE_OPERATION_OK);
        } else {
            result.setResult(Constants.HTTP_SYSTEM_ERROR, Constants.MESSAGE_OPERATION_ERROR);
        }
        return result;
    }
}
