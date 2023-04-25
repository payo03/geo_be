package com.spring.geo.task.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.spring.geo.common.exception.BusinessException;
import com.spring.geo.task.model.Member;

@Mapper
@Repository
public interface MemberRepository {
    
    public int registerMember(Member member) throws BusinessException;
    public List<Member> selectMemberList() throws BusinessException;
    public Member loginMember(Member member) throws BusinessException;
    public int updateStatus(Member resultMember) throws BusinessException;
}
