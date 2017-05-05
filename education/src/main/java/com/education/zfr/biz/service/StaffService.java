package com.education.zfr.biz.service;

import com.education.zfr.biz.dao.StaffDao;
import com.education.zfr.biz.entity.CpnDepartmentStaffRel;
import com.education.zfr.biz.entity.CpnStaff;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by bob on 2017/4/25.
 */
@Service
public class StaffService {

    @Autowired
    private StaffDao staffDao;

    public CpnStaff findByLoginNameAndLoginPwd(String loginName,String LoginPwd){
        return staffDao.findByLoginNameAndLoginPwd(loginName, LoginPwd);
    }

    public List<CpnStaff> getStaffList(){
        List<CpnStaff> cpnStaffList = Lists.newArrayList();
        Iterable<CpnStaff> cpnStaffs = staffDao.findAll();
        for(CpnStaff cpnStaff : cpnStaffs){
            cpnStaffList.add(cpnStaff);
        }
        return cpnStaffList;
    }

    public CpnStaff findByStaffNumber(String staffNumber){
        return staffDao.findByStaffNumber(staffNumber);
    }

    public void saveStaff(CpnStaff staff){
        staffDao.save(staff);
    }

    public CpnStaff findStaffByStaffId(Long staffId){
        return staffDao.findByStaffId(staffId);
    }


}
