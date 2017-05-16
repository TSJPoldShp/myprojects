package com.education.zfr.biz.web;

import com.education.zfr.biz.service.PositionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * Created by zangfr on 2017/5/5.
 */
@Controller
public class PositionController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PositionController.class);

    @Autowired
    private PositionService positionService;

    public String getPositionName(Long positionId){
        return positionService.getNameByPositionId(positionId);
    }
}
