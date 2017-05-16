package com.education.zfr.biz.service;

import com.education.zfr.biz.dao.PositionStaffRelDao;
import com.education.zfr.biz.entity.CpnPositionStaffRel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by zangfr on 2017/4/25.
 */
@Service
public class PositionStaffRelService {

    @Autowired
    private PositionStaffRelDao positionStaffRelDao;

    public CpnPositionStaffRel findByStaffIdAndPositionId(Long staffId,Long positionId){
        return positionStaffRelDao.findByStaffIdAndPositionId(staffId,positionId);
    }

    public void savePositionStaffRel(CpnPositionStaffRel positionStaffRel){
        positionStaffRelDao.save(positionStaffRel);
    }

    public CpnPositionStaffRel findByStaffId(Long staffId) {
        return positionStaffRelDao.findByStaffId(staffId);
    }

    public boolean deleteByStaffId(Long staffId) {
        CpnPositionStaffRel positionStaffRel = positionStaffRelDao.findByStaffId(staffId);
        positionStaffRelDao.delete(positionStaffRel);
        return true;
    }
}
