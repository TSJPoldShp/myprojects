package com.education.zfr.biz.web;

import com.education.zfr.biz.entity.CpnDepartment;
import com.education.zfr.biz.service.DepartmentService;
import com.education.zfr.common.mvc.Servlets;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.ServletRequest;
import java.util.List;
import java.util.Map;

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
    public String departmentList(@RequestParam(value = "pageNum", defaultValue = "1") int pageNumber,
                                 @RequestParam(value = "numPerPage", defaultValue = "20") int pageSize,
                                 Model model, ServletRequest request){
        Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
        Page<CpnDepartment> departments = departmentService.getDepartmentList(searchParams, pageNumber, pageSize);
        model.addAttribute("departments",departments);
        model.addAttribute("totalCount",departments.getTotalElements());
        model.addAttribute("searchParams", Servlets.encodeParameterStringWithPrefix(searchParams, "search_"));
        model.addAttribute("pageNum", pageNumber);
        model.addAttribute("numPerPage", pageSize);
        return "biz/department/departmentList";
    }

    public String getDepartmentName(Long departmentId){
        return departmentService.getNameByDepartmentId(departmentId);
    }

}
