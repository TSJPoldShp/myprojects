package com.education.zfr.biz.service;

import com.education.zfr.biz.dao.PositionDao;
import com.education.zfr.biz.entity.CpnPosition;
import com.education.zfr.common.mvc.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Map;

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

    public Page<CpnPosition> getPositionList(Map<String, Object> searchParams, int pageNumber, int pageSize) {
        PageRequest pageRequest = PageUtil.buildPageRequest(pageNumber, pageSize,"desc", "positionId");
        Specification<CpnPosition> spec = PageUtil.buildSpecification(searchParams, CpnPosition.class);
        return positionDao.findAll(spec, pageRequest);
    }

    public void save(CpnPosition position) {
        positionDao.save(position);
    }

    public CpnPosition getPositionByNameAndDepartment(String positionName, Long departmentId) {
        return positionDao.findByPositionNameAndDepartmentId(positionName,departmentId);
    }

    public CpnPosition findByPositionId(Long id) {
        return positionDao.findByPositionId(id);
    }

    public void deletePosition(Long positionId) {
        positionDao.delete(positionId);
    }

    public Long getIdByPositionName(String positionName) {
        Long positionId;
        try{
            positionId = positionDao.findByPositionName(positionName).getPositionId();
        }catch(Exception e){
            positionId = 0L;
        }
        return positionId;
    }

    public Long getIdByPositionNameAndDepartmentId(String positionName, Long departmentId) {
        Long positionId;
        try{
            positionId = positionDao.findByPositionNameAndDepartmentId(positionName,departmentId).getPositionId();
        }catch (Exception e){
            positionId = 0L;
        }
        return positionId;
    }

}
