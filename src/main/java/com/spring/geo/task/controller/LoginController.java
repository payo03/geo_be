package com.spring.geo.task.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

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
public class LoginController {

    @Autowired
    CommonUtilService utilService;

    @Resource(name = "memberService")
    private MemberService memberService;

    @ApiOperation(httpMethod = "POST", notes = "login")
    @RequestMapping(method = RequestMethod.POST, path = "/rest/vst/login")
    public Map<String, Object> loginMember(@RequestBody Member member, HttpServletResponse response) throws Exception {
        Map<String, Object> result = new HashMap<String, Object>();

        String token = utilService.createToken(member.getMemberId()); // 1시간
        response.setHeader("loginauth", token);
        
        Member resultMember = memberService.loginMember(member);
        result.put("member", resultMember);

        return result;
    }
    
}
