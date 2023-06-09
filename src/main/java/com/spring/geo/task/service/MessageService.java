package com.spring.geo.task.service;

import java.util.List;

import com.spring.geo.common.exception.BusinessException;
import com.spring.geo.task.model.Message;

public interface MessageService {
    
    public List<Message> messageHandler(Message message) throws BusinessException;
}
