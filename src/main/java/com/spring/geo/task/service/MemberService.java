package com.spring.geo.task.service;

import com.spring.geo.common.exception.BusinessException;
import com.spring.geo.task.model.Memberonline;

public interface MemberService {
    
    public Memberonline selectMember() throws BusinessException;
}
