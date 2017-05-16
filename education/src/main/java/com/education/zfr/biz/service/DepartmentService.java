package com.education.zfr.biz.service;

import com.education.zfr.biz.dao.DepartmentDao;
import com.education.zfr.biz.entity.CpnDepartment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zangfr on 2017/5/5.
 */
@Service
public class DepartmentService {

    @Autowired
    private DepartmentDao departmentDao;

    public List<CpnDepartment> getDepartmentList() {
        return (List)departmentDao.findAll();
    }
}
