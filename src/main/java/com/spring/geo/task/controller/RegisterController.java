package com.spring.geo.task.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.spring.geo.common.config.ApiOperation;
import com.spring.geo.common.util.CommonUtilService;
import com.spring.geo.task.model.Member;
import com.spring.geo.task.service.MemberService;

@RestController
public class RegisterController {

    @Autowired
    CommonUtilService utilService;

    @Resource(name = "memberService")
    private MemberService memberService;

    @ApiOperation(httpMethod = "POST", notes = "register")
    @RequestMapping(method = RequestMethod.POST, path = "/vst/register")
    public Map<String, Object> registerMember(@RequestBody Member member) throws Exception {
        memberService.registerMember(member);

        Map<String, Object> result = new HashMap<String, Object>();
        
        return result;
    }
    
}
