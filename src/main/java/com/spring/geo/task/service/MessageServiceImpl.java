package com.spring.geo.task.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.geo.common.exception.BusinessException;
import com.spring.geo.task.model.Message;
import com.spring.geo.task.repository.MessageRepository;

@Service("messageService")
public class MessageServiceImpl implements MessageService {

    @Autowired
    MessageRepository messageRepository;

    @Override
    public List<Message> messageHandler(Message message) throws BusinessException {
        if (message.getContent() != null) messageRepository.sendMessage(message);

        List<Message> messageList = messageRepository.selectMessageList(message);
        return messageList;
    }

}
