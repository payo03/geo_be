package com.spring.geo.task.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.spring.geo.task.model.Message;
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
    public Map<String, Object> loginMember(@RequestBody Member param, HttpServletResponse response) throws Exception {
        Map<String, Object> result = new HashMap<String, Object>();

        String token = utilService.createToken(param.getMemberId()); // 30분
        response.setHeader("loginauth", token);
        
        Member member = memberService.loginMember(param);
        List<Member> memberList = memberService.selectMemberList();
        List<Message> messageList = messageService.selectMessageList(member.getMemberNumber());

        result.put("member", member);
        result.put("memberList", memberList);
        result.put("messageList", messageList);

        return result;
    }
    
}
