package com.education.zfr.biz.dao;

import com.education.zfr.biz.entity.CpnDepartmentStaffRel;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by bob on 2017/4/25.
 */
public interface DepartmentStaffRelDao extends PagingAndSortingRepository<CpnDepartmentStaffRel,Long>,JpaSpecificationExecutor<CpnDepartmentStaffRel> {
    CpnDepartmentStaffRel findByStaffIdAndDepartmentId(Long staffId,Long departmentId);

    CpnDepartmentStaffRel findByStaffId(Long staffId);
}
