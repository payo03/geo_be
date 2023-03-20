package com.spring.geo.task.repository;

import org.apache.ibatis.annotations.Mapper;

import com.spring.geo.common.exception.BusinessException;
import com.spring.geo.task.model.Memberonline;

@Mapper
public interface MemberRepository {
    
    public Memberonline selectMember() throws BusinessException;
}
