package com.education.zfr.biz.dao;

import com.education.zfr.biz.entity.CpnPositionStaffRel;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by bob on 2017/4/25.
 */
public interface PositionStaffRelDao extends PagingAndSortingRepository<CpnPositionStaffRel,Long>,JpaSpecificationExecutor<CpnPositionStaffRel> {
    CpnPositionStaffRel findByStaffIdAndPositionId(Long staffId,Long positionId);

    CpnPositionStaffRel findByStaffId(Long staffId);
}
