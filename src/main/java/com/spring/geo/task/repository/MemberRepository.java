package com.spring.geo.task.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.spring.geo.common.exception.BusinessException;
import com.spring.geo.task.model.Member;
import com.spring.geo.task.model.Memberonline;

@Mapper
@Repository
public interface MemberRepository {
    
    public List<Memberonline> selectMember() throws BusinessException;
    public void registerMember(Member member) throws BusinessException;
    public List<Member> selectMemberList() throws BusinessException;
}
