package com.spring.geo.task.service;

import java.util.List;

import com.spring.geo.common.exception.BusinessException;
import com.spring.geo.task.model.Member;

public interface MemberService {
    
    public int registerMember(Member member) throws BusinessException;
    public List<Member> selectMemberList() throws BusinessException;
    public Member loginMember(Member member) throws BusinessException;
    public int updateStatus(Member member) throws BusinessException;
}
