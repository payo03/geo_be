package com.spring.geo.task.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.geo.common.exception.BusinessException;
import com.spring.geo.task.model.Member;
import com.spring.geo.task.model.Memberonline;
import com.spring.geo.task.repository.MemberRepository;

@Service("memberService")
public class MemberServiceImpl implements MemberService {

    @Autowired
    MemberRepository memberRepository;
    
    @Override
    public List<Memberonline> selectMember() throws BusinessException {
        List<Memberonline> statusList = memberRepository.selectMember();

        return statusList;
    }

    @Override
    public void registerMember(Member member) throws BusinessException {
        memberRepository.registerMember(member);
    }

    @Override
    public List<Member> selectMemberList() throws BusinessException {
        List<Member> memberList = memberRepository.selectMemberList();

        return memberList;
    }

    @Override
    public Member loginMember(Member member) throws BusinessException {
        Member resultMember = memberRepository.loginMember(member);

        if(resultMember == null) {
            throw new BusinessException("login fail");
        }
        return resultMember;
    }
    
}
