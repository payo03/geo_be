package com.spring.geo.task.controller;

import java.time.Duration;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    
    private long time = Duration.ofMinutes(30).toMillis();

    @Value("${jwt.custom.access-token}")
    private String accessToken;

    @Value("${jwt.custom.refresh-token}")
    private String refreshToken;

    @Autowired
    UtilService utilService;

    @Resource(name = "memberService")
    private MemberService memberService;

    @Resource(name = "messageService")
    private MessageService messageService;

    @ApiOperation(httpMethod = "POST", notes = "login")
    @RequestMapping(method = RequestMethod.POST, path = "/rest/vst/login")
    public Member loginMember(@RequestBody Member param, HttpServletResponse response) throws Exception {
        response.setHeader("accesstoken", utilService.createToken(param.getMemberId(), accessToken));
        response.setHeader("refreshtoken", utilService.createToken(param.getMemberId(), accessToken + refreshToken, time));
        
        Member result = memberService.loginMember(param);
        return result;
    }
    
}
