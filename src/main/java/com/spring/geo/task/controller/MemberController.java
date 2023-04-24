package com.spring.geo.task.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.spring.geo.common.config.ApiOperation;
import com.spring.geo.task.model.Member;
import com.spring.geo.task.service.MemberService;

@RestController
public class MemberController {

    @Resource(name = "memberService")
    private MemberService memberService;

    @ApiOperation(httpMethod = "POST", notes = "memberList")
    @RequestMapping(method = RequestMethod.POST, path = "/rest/vst/memberList")
    public List<Member> selectMemberList() throws Exception {
        List<Member> memberList = memberService.selectMemberList();
        
        return memberList;
    }
    
    @ApiOperation(httpMethod = "POST", notes = "updateStatus")
    @RequestMapping(method = RequestMethod.POST, path = "/rest/auth/updatestatus")
    public void updateStatus(@RequestBody Member member) throws Exception {
        memberService.updateStatus(member);
    }
}
