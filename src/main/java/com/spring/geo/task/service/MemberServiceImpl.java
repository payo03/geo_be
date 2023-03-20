package com.spring.geo.task.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.geo.common.exception.BusinessException;
import com.spring.geo.task.model.Memberonline;
import com.spring.geo.task.repository.MemberRepository;

@Service("memberService")
public class MemberServiceImpl implements MemberService {

    @Autowired
    MemberRepository memberRepository;
    
    @Override
    public Memberonline selectMember() throws BusinessException {
        
        Memberonline member = memberRepository.selectMember();
        return member;
    }
    
}
