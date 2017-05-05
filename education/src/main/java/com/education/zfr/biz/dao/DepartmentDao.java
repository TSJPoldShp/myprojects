package com.education.zfr.biz.dao;

import com.education.zfr.biz.entity.CpnDepartment;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by zangfr on 2017/5/5.
 */
public interface DepartmentDao extends PagingAndSortingRepository<CpnDepartment,Long>,JpaSpecificationExecutor<CpnDepartment> {
}
