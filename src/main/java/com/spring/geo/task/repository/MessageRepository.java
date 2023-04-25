package com.spring.geo.task.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.spring.geo.common.exception.BusinessException;
import com.spring.geo.task.model.Message;

@Mapper
@Repository
public interface MessageRepository {

    int sendMessage(Message message) throws BusinessException;
    List<Message> selectMessageList(int memberNumber) throws BusinessException;

}
