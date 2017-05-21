package com.education.zfr.biz.web;

import com.education.zfr.biz.entity.CpnDepartment;
import com.education.zfr.biz.service.DepartmentService;
import com.education.zfr.common.mvc.HttpResult;
import com.education.zfr.common.mvc.Servlets;
import com.education.zfr.common.utils.Constants;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
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
     *
     * @param model
     * @return
     */
    @RequestMapping("departmentList")
    public String departmentList(@RequestParam(value = "pageNum", defaultValue = "1") int pageNumber,
                                 @RequestParam(value = "numPerPage", defaultValue = "20") int pageSize,
                                 Model model, ServletRequest request) {
        Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
        Page<CpnDepartment> departments = departmentService.getDepartmentList(searchParams, pageNumber, pageSize);
        model.addAttribute("departments", departments);
        model.addAttribute("totalCount", departments.getTotalElements());
        model.addAttribute("searchParams", Servlets.encodeParameterStringWithPrefix(searchParams, "search_"));
        model.addAttribute("pageNum", pageNumber);
        model.addAttribute("numPerPage", pageSize);
        return "biz/department/departmentList";
    }

    @RequestMapping("toAddDepartment")
    public String toAddDepartment() {
        return "biz/department/addDepartment";
    }

    /**
     * 新增部门
     *
     * @param department
     * @return
     */
    @RequestMapping("addDepartment")
    @ResponseBody
    public HttpResult addDepartment(CpnDepartment department) {
        HttpResult result = new HttpResult();
        try {
            Long departmentId = departmentService.getIdByDepartmentName(department.getParentName());
            if (0 != departmentId) {
                if (null == departmentService.getDepartmentByName(department.getDepartmentName())) {
                    department.setParentId(departmentId);
                    departmentService.save(department);
                    result.setResult(Constants.HTTP_SYSTEM_OK, Constants.MESSAGE_OPERATION_OK);
                } else {
                    result.setResult(Constants.HTTP_SYSTEM_ERROR, "部门已存在");
                }
            } else {
                result.setResult(Constants.HTTP_SYSTEM_ERROR, "父部门不存在");
            }
        } catch (Exception e) {
            result.setResult(Constants.HTTP_SYSTEM_ERROR, Constants.MESSAGE_OPERATION_ERROR);
        }
        return result;
    }

    @RequestMapping("toUpdateDepartment/{id}")
    public String toUpdateDepartment(@PathVariable("id") Long id, Model model) {
        CpnDepartment department = departmentService.getDepartmentById(id);
        model.addAttribute("department", department);
        return "biz/department/departmentInfo";
    }

    /**
     * 更新部门
     *
     * @param department
     * @return
     */
    @RequestMapping("updateDepartment")
    @ResponseBody
    public HttpResult updateDepartment(CpnDepartment department) {
        HttpResult result = new HttpResult();
        try {
            Long departmentId = departmentService.getIdByDepartmentName(department.getParentName());
            department.setParentId(departmentId);
            departmentService.save(department);
            result.setResult(Constants.HTTP_SYSTEM_OK, Constants.MESSAGE_OPERATION_OK);
        } catch (Exception e) {
            result.setResult(Constants.HTTP_SYSTEM_ERROR, Constants.MESSAGE_OPERATION_ERROR);
        }
        return result;
    }

    /**
     * 删除部门
     *
     * @param request
     * @return
     */
    @RequestMapping("delete")
    @ResponseBody
    public HttpResult deleteDepartment(HttpServletRequest request) {
        HttpResult result = new HttpResult();
        String[] ids = request.getParameterValues("ids");
        try {
            Map<String, List<Long>> checkResult = checkDepartment(ids);
            List<Long> deleteIdList = checkResult.get("deleteIds");
            List<Long> undeleteIdList = checkResult.get("undeleteIds");
            List<Long> deleteIds = Lists.newArrayList();
            StringBuffer undeleteIds = new StringBuffer();
            if (!CollectionUtils.isEmpty(deleteIdList)) {
                for (Long id : deleteIdList) {
                    deleteIds.add(id);
                }
            }else{
                result.setResult(Constants.HTTP_SYSTEM_ERROR,"删除失败");
            }
            if (CollectionUtils.isEmpty(undeleteIdList)) {
                for (Long id : deleteIds) {
                    departmentService.delete(id);
                }
                result.setResult(Constants.HTTP_SYSTEM_OK, Constants.MESSAGE_OPERATION_OK);
            } else {
                for (Long id : deleteIds) {
                    departmentService.delete(id);
                }
                for(Long id : undeleteIdList){
                    if(StringUtils.isBlank(undeleteIds.toString())) {
                        undeleteIds.append(id);
                    }else{
                        undeleteIds.append(",").append(id);
                    }
                }
                result.setResult(Constants.HTTP_SYSTEM_OK, "删除成功，其中部门Id为：" + undeleteIds + "的部门删除失败，原因：存在子部门");
            }
        } catch (Exception e) {
            result.setResult(Constants.HTTP_SYSTEM_ERROR, Constants.MESSAGE_OPERATION_ERROR);
        }
        return result;
    }

    private Map<String, List<Long>> checkDepartment(String[] ids) {
        Map<String, List<Long>> checkResult = Maps.newHashMap();
        List<Long> deleteIds = Lists.newArrayList();
        List<Long> undeleteIds = Lists.newArrayList();
        for (String id : ids) {
            Long departmentId = Long.valueOf(id);
            List<CpnDepartment> departments = departmentService.getDepartmentByParentId(departmentId);
            if (CollectionUtils.isEmpty(departments)) {
                deleteIds.add(departmentId);
            } else {
                undeleteIds.add(departmentId);
            }
        }
        checkResult.put("deleteIds", deleteIds);
        checkResult.put("undeleteIds", undeleteIds);
        return checkResult;
    }

    public String getDepartmentName(Long departmentId) {
        return departmentService.getNameByDepartmentId(departmentId);
    }

}
