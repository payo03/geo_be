package com.spring.geo.task.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.geo.common.config.ApiOperation;
import com.spring.geo.common.util.CommonUtilService;
import com.spring.geo.task.model.LoginModel;
import com.spring.geo.task.service.MemberService;

@RestController
public class LoginController {

    @Autowired
    CommonUtilService utilService;

    @Resource(name = "memberService")
    private MemberService memberService;

    @ApiOperation(httpMethod = "POST", notes = "login")
    @RequestMapping(method = RequestMethod.POST, path = "/login")
    public Map<String, Object> login(@RequestBody Map<String, Object> map) throws Exception {
        
        LoginModel loginModel = utilService.convertMapToModel(map, LoginModel.class);
        Map<String, Object> result = new HashMap<String, Object>();

        result.put("email", loginModel.getEmail());
        result.put("remember", loginModel.isRemember());
        
        return result;
    }
    
}
