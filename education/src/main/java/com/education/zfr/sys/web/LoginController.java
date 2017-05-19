package com.education.zfr.sys.web;

import com.education.zfr.biz.entity.CpnStaff;
import com.education.zfr.biz.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class LoginController {

    @Autowired
    public StaffService staffService;

    @RequestMapping("login")
    public String login(){
        return "login/login";
    }

    @RequestMapping("index")
    public String getLogin(@RequestParam(value="username")String username,@RequestParam(value="password")String password){
        CpnStaff cpnStaff = staffService.findByLoginNameAndLoginPwd(username,password);
        if( null != cpnStaff){
            return "index";
        }
        return "/login/login";
    }

    @RequestMapping("index1")
    public String getIndex(){
        return "index";
    }


}
