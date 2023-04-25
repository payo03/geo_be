package com.spring.geo.task.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.geo.common.exception.BusinessException;
import com.spring.geo.task.model.Member;
import com.spring.geo.task.repository.MemberRepository;

@Service("memberService")
public class MemberServiceImpl implements MemberService {

    @Autowired
    MemberRepository memberRepository;
    
    @Override
    public int registerMember(Member member) throws BusinessException {
        int result = memberRepository.registerMember(member);

        return result;
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
        } else {
            
            resultMember.setMemberStatus(1);
            this.updateStatus(resultMember);
        }

        return resultMember;
    }

    @Override
    public int updateStatus(Member member) throws BusinessException {
        int result = memberRepository.updateStatus(member);

        return result;
    }
    
}
