package com.education.zfr.biz.service;

import com.education.zfr.biz.dao.StaffDao;
import com.education.zfr.biz.entity.CpnStaff;
import com.education.zfr.common.mvc.PageUtil;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by zangfr on 2017/4/25.
 */
@Service
public class StaffService {

    @Autowired
    private StaffDao staffDao;

    public CpnStaff findByLoginNameAndLoginPwd(String loginName, String LoginPwd) {
        return staffDao.findByLoginNameAndLoginPwd(loginName, LoginPwd);
    }

    public Page<CpnStaff> getStaffList(Map<String, Object> searchParams, int pageNumber, int pageSize) {
        PageRequest pageRequest = PageUtil.buildPageRequest(pageNumber, pageSize,"desc", "staffId");
        Specification<CpnStaff> spec = PageUtil.buildSpecification(searchParams, CpnStaff.class);
        return staffDao.findAll(spec, pageRequest);
    }

    public CpnStaff findByStaffNumber(String staffNumber) {
        return staffDao.findByStaffNumber(staffNumber);
    }

    public void saveStaff(CpnStaff staff) {
        staffDao.save(staff);
    }

    public CpnStaff findStaffByStaffId(Long staffId) {
        return staffDao.findByStaffId(staffId);
    }


    public Boolean deleteStaffById(Long staffId) {
        CpnStaff staff = staffDao.findByStaffId(staffId);
        staffDao.delete(staff);
        return true;
    }
}
