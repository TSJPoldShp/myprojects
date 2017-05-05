package com.education.zfr.biz.web;

import com.education.zfr.biz.service.SingleTableService;
import com.education.zfr.common.mvc.HttpResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by zangfr on 2017/4/24.
 */
@Controller
public class SingleTableController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SingleTableController.class);

    @Autowired
    private SingleTableService singleTableService;

    @RequestMapping("importExcel")
    public HttpResult importExcel(HttpServletRequest request){
        HttpResult result = new HttpResult();
        try {
            result = singleTableService.importExcel(request);
        } catch (Exception e) {
            result.setResult("300", "操作失败");
            LOGGER.error("Exit func[importExcel()] throw exception", e);
        }
        return result;
    }
}
