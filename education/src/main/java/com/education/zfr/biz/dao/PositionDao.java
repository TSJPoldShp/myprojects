package com.education.zfr.biz.dao;

import com.education.zfr.biz.entity.CpnPosition;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by zangfr on 2017/5/5.
 */
public interface PositionDao  extends PagingAndSortingRepository<CpnPosition,Long>,JpaSpecificationExecutor<CpnPosition> {
    CpnPosition findByPositionId(Long positionId);
}
