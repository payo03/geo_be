package com.spring.geo.task.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.spring.geo.common.config.ApiOperation;
import com.spring.geo.common.util.UtilService;
import com.spring.geo.task.model.Member;
import com.spring.geo.task.service.MessageService;
import com.spring.geo.task.service.MemberService;

@RestController
public class LoginController {

    @Autowired
    UtilService utilService;

    @Resource(name = "memberService")
    private MemberService memberService;

    @Resource(name = "messageService")
    private MessageService messageService;

    @ApiOperation(httpMethod = "POST", notes = "login")
    @RequestMapping(method = RequestMethod.POST, path = "/rest/vst/login")
    public Member loginMember(@RequestBody Member param, HttpServletResponse response) throws Exception {
        String token = utilService.createToken(param.getMemberId()); // 30분
        response.setHeader("loginauth", token);
        
        Member result = memberService.loginMember(param);
        return result;
    }
    
}
