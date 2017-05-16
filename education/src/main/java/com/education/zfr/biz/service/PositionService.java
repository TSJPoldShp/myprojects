package com.education.zfr.biz.service;

import com.education.zfr.biz.dao.PositionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by zangfr on 2017/5/5.
 */
@Service
public class PositionService {

    @Autowired
    private PositionDao positionDao;

    public String getNameByPositionId(Long positionId) {
        String positionName;
        try{
            positionName = positionDao.findByPositionId(positionId).getPositionName();
        }catch (Exception e){
            positionName = "无";
        }
        return positionName;
    }
}
