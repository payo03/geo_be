package com.spring.geo.task.service;

import java.util.List;

import com.spring.geo.common.exception.BusinessException;
import com.spring.geo.task.model.Memberonline;

public interface MemberService {
    
    public List<Memberonline> selectMember() throws BusinessException;
}
