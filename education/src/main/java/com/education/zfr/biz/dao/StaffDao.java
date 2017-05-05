package com.education.zfr.biz.dao;

import com.education.zfr.biz.entity.CpnStaff;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by zangfr on 2017/4/25.
 */
public interface StaffDao extends PagingAndSortingRepository<CpnStaff,Long>,JpaSpecificationExecutor<CpnStaff>{

    CpnStaff findByLoginNameAndLoginPwd(String loginName,String loginPwd);

    CpnStaff findByStaffNumber(String staffNumber);

    CpnStaff findByStaffId(Long staffId);
}
