package com.education.zfr.biz.web;

import com.education.zfr.biz.entity.CpnDepartmentStaffRel;
import com.education.zfr.biz.entity.CpnPositionStaffRel;
import com.education.zfr.biz.entity.CpnStaff;
import com.education.zfr.biz.service.DepartmentStaffRelService;
import com.education.zfr.biz.service.PositionStaffRelService;
import com.education.zfr.biz.service.StaffService;
import com.education.zfr.common.mvc.HttpResult;
import com.education.zfr.common.utils.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Created by zangfr on 2017/4/19.
 */
@Controller
@RequestMapping("staff")
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
    public String getStaffList(Model model) {
        List<CpnStaff> cpnStaffList = staffService.getStaffList();
        model.addAttribute("cpnStaffs", cpnStaffList);
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
     * @startuml
     * start
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
    @RequestMapping("addStaff")
    public HttpResult addStaff(@ModelAttribute("staff") CpnStaff staff) {
        HttpResult result = new HttpResult();
        CpnDepartmentStaffRel cpnDepartmentStaffRel = new CpnDepartmentStaffRel();
        CpnPositionStaffRel cpnPositionStaffRel = new CpnPositionStaffRel();
        try {
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
    @RequestMapping("toUpdateStaff/{staffId}")
    public String toUpdateStaff(@PathVariable("staffId") Long staffId, Model model) {
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
    public HttpResult updateStaff(@ModelAttribute("staff") CpnStaff staff) {
        HttpResult result = new HttpResult();
        CpnDepartmentStaffRel cpnDepartmentStaffRel = departmentStaffRelService.findByStaffId(staff.getStaffId());
        cpnDepartmentStaffRel.setDepartmentId(staff.getDepartmentId());
        CpnPositionStaffRel cpnPositionStaffRel = positionStaffRelService.findByStaffId(staff.getStaffId());
        cpnPositionStaffRel.setPositionId(staff.getPositionId());
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
    @RequestMapping("delete")
    public HttpResult deleteStaff(@PathVariable("staffId") Long staffId) {
        HttpResult result = new HttpResult();
        if (staffService.deleteStaffById(staffId)) {
            result.setResult(Constants.HTTP_SYSTEM_OK, Constants.MESSAGE_OPERATION_OK);
        } else {
            result.setResult(Constants.HTTP_SYSTEM_ERROR, Constants.MESSAGE_OPERATION_ERROR);
        }
        return result;
    }
}
