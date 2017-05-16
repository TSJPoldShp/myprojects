package com.education.zfr.biz.service;

import com.education.zfr.biz.dao.DepartmentDao;
import com.education.zfr.biz.entity.CpnDepartment;
import com.education.zfr.common.mvc.PageUtil;
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
}
