package com.education.zfr.biz.service;

import com.education.zfr.biz.dao.DepartmentStaffRelDao;
import com.education.zfr.biz.entity.CpnDepartmentStaffRel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by bob on 2017/4/25.
 */
@Service
public class DepartmentStaffRelService {

    @Autowired
    private DepartmentStaffRelDao departmentStaffRelDao;

    public void saveDepartmentStaffRel(CpnDepartmentStaffRel cpnDepartmentStaffRel){
        departmentStaffRelDao.save(cpnDepartmentStaffRel);
    }

    public CpnDepartmentStaffRel findByStaffIdAndDepartmentId(Long staffId,Long departmentId){
        return departmentStaffRelDao.findByStaffIdAndDepartmentId(staffId,departmentId);
    }

    public CpnDepartmentStaffRel findByStaffId(Long staffId) {
        return departmentStaffRelDao.findByStaffId(staffId);
    }
}
