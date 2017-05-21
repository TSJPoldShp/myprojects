package com.education.zfr.biz.web;

import com.education.zfr.biz.entity.CpnDepartment;
import com.education.zfr.biz.entity.CpnPosition;
import com.education.zfr.biz.service.DepartmentService;
import com.education.zfr.biz.service.PositionService;
import com.education.zfr.common.mvc.HttpResult;
import com.education.zfr.common.mvc.Servlets;
import com.education.zfr.common.utils.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by zangfr on 2017/5/5.
 */
@Controller
@RequestMapping("position")
public class PositionController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PositionController.class);

    @Autowired
    private PositionService positionService;

    @Autowired
    private DepartmentService departmentService;

    /**
     * 进入职位管理页面
     *
     * @param pageNumber
     * @param pageSize
     * @param model
     * @param request
     * @return
     */
    @RequestMapping("positionList")
    public String positionList(@RequestParam(value = "pageNum", defaultValue = "1") int pageNumber,
                               @RequestParam(value = "numPerPage", defaultValue = "20") int pageSize,
                               Model model, ServletRequest request) {
        Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
        Page<CpnPosition> positions = positionService.getPositionList(searchParams, pageNumber, pageSize);
        model.addAttribute("positions", positions);
        model.addAttribute("totalCount", positions.getTotalElements());
        model.addAttribute("searchParams", Servlets.encodeParameterStringWithPrefix(searchParams, "search_"));
        model.addAttribute("pageNum", pageNumber);
        model.addAttribute("numPerPage", pageSize);
        return "/biz/position/positionList";
    }

    @RequestMapping("toAddPosition")
    public String toAddPosition() {
        return "biz/position/addPosition";
    }

    /**
     * 新增职位
     *
     * @param position
     * @return
     */
    @RequestMapping("addPosition")
    @ResponseBody
    public HttpResult addPosition(CpnPosition position) {
        HttpResult result = new HttpResult();
        try {
            Long departmentId = departmentService.getIdByDepartmentName(position.getDepartmentName());
            if (0 != departmentId) {
                position.setDepartmentId(departmentId);
                positionService.save(position);
                result.setResult(Constants.HTTP_SYSTEM_OK, Constants.MESSAGE_OPERATION_OK);
            } else {
                result.setResult(Constants.HTTP_SYSTEM_ERROR, "部门不存在");
            }
        } catch (Exception e) {
            result.setResult(Constants.HTTP_SYSTEM_ERROR, Constants.MESSAGE_OPERATION_ERROR);
        }
        return result;
    }

    @RequestMapping("toUpdatePosition/{id}")
    public String toUpdatePosition(@PathVariable("id") Long id, Model model) {
        CpnPosition position = positionService.findByPositionId(id);
        model.addAttribute("position", position);
        return "biz/position/positionInfo";
    }

    /**
     * 更新职位
     *
     * @param position
     * @return
     */
    @RequestMapping("updatePosition")
    @ResponseBody
    public HttpResult updatePosition(CpnPosition position) {
        HttpResult result = new HttpResult();
        try {
            Long departmentId = departmentService.getIdByDepartmentName(position.getDepartmentName());
            if (0 != departmentId) {
                if (null == positionService.getPositionByNameAndDepartment(position.getPositionName(), departmentId)) {
                    position.setDepartmentId(departmentId);
                    positionService.save(position);
                    result.setResult(Constants.HTTP_SYSTEM_OK, Constants.MESSAGE_OPERATION_OK);
                } else {
                    result.setResult(Constants.HTTP_SYSTEM_ERROR, "职位已存在");
                }
            } else {
                result.setResult(Constants.HTTP_SYSTEM_ERROR, "部门不存在");
            }
        } catch (Exception e) {
            result.setResult(Constants.HTTP_SYSTEM_ERROR, Constants.MESSAGE_OPERATION_ERROR);
        }
        return result;
    }

    @RequestMapping("delete")
    @ResponseBody
    public HttpResult deletePosition(HttpServletRequest request) {
        HttpResult result = new HttpResult();
        String[] ids = request.getParameterValues("ids");
        try {
            for (String id : ids) {
                Long positionId = Long.valueOf(id);
                positionService.deletePosition(positionId);
            }
            result.setResult(Constants.HTTP_SYSTEM_OK, Constants.MESSAGE_OPERATION_OK);
        } catch (Exception e) {
            result.setResult(Constants.HTTP_SYSTEM_ERROR, Constants.MESSAGE_OPERATION_ERROR);
        }
        return result;
    }

    public String getPositionName(Long positionId) {
        return positionService.getNameByPositionId(positionId);
    }
}
