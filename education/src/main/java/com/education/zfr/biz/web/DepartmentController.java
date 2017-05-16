package com.education.zfr.biz.web;

import com.education.zfr.biz.entity.CpnDepartment;
import com.education.zfr.biz.service.DepartmentService;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Created by zangfr on 2017/5/5.
 */
@Controller
@RequestMapping("department")
public class DepartmentController {

    private static final Logger LOGGER = LoggerFactory.getLogger(DepartmentController.class);

    @Autowired
    private DepartmentService departmentService;

    /**
     * 进入部门管理页面
     * @param model
     * @return
     */
    @RequestMapping("departmentList")
    public String departmentList(Model model){
        List<CpnDepartment> departments = departmentService.getDepartmentList();
        model.addAttribute("departments",departments);
        return "biz/department/departmentList";
    }

}
