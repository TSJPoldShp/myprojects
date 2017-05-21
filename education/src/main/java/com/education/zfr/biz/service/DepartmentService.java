package com.education.zfr.biz.service;

import com.education.zfr.biz.dao.DepartmentDao;
import com.education.zfr.biz.entity.CpnDepartment;
import com.education.zfr.common.mvc.PageUtil;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by zangfr on 2017/5/5.
 */
@Service
public class DepartmentService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DepartmentService.class);

    @Autowired
    private DepartmentDao departmentDao;

    public Page<CpnDepartment> getDepartmentList(Map<String, Object> searchParams, int pageNumber, int pageSize) {
        PageRequest pageRequest = PageUtil.buildPageRequest(pageNumber, pageSize,"desc", "departmentId");
        Specification<CpnDepartment> spec = PageUtil.buildSpecification(searchParams, CpnDepartment.class);
        return departmentDao.findAll(spec, pageRequest);
    }

    public String getNameByDepartmentId(Long departmentId) {
        String departmentName;
        try{
            departmentName = departmentDao.findByDepartmentId(departmentId).getDepartmentName();
        }catch (Exception e){
            departmentName = "无";
        }
        return departmentName;
    }

    public Long getIdByDepartmentName(String departmentName) {
        Long departmentId;
        try{
            departmentId = departmentDao.findByDepartmentName(departmentName).getDepartmentId();
        }catch (Exception e){
            departmentId = 0L;
        }
        return departmentId;
    }

    public void save(CpnDepartment department) {
        departmentDao.save(department);
    }

    public CpnDepartment getDepartmentById(Long id) {
        return departmentDao.findByDepartmentId(id);
    }

    public void delete(Long departmentId) {
        departmentDao.delete(departmentId);
    }

    public CpnDepartment getDepartmentByName(String departmentName) {
        return departmentDao.findByDepartmentName(departmentName);
    }

    public List<CpnDepartment> getDepartmentByParentId(Long departmentId) {
        List<CpnDepartment> departments = Lists.newArrayList();
        try{
            departments = departmentDao.findByParentId(departmentId);
        }catch(Exception e){
            departments = null;
            LOGGER.error("throw exception.",e);
        }
        return departments;
    }
}
