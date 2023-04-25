package com.spring.geo.task.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.geo.common.exception.BusinessException;
import com.spring.geo.task.model.Message;
import com.spring.geo.task.repository.MemberRepository;
import com.spring.geo.task.repository.MessageRepository;

@Service("messageService")
public class MessageServiceImpl implements MessageService {

    @Autowired
    MessageRepository messageRepository;

    @Autowired
    MemberRepository memberRepository;

    @Override
    public Message messageHandler(Message message) throws BusinessException {

        messageRepository.sendMessage(message);

        return message;
    }

    @Override
    public List<Message> selectMessageList(int memberNumber) {
        List<Message> messageList = messageRepository.selectMessageList(memberNumber);

        return messageList;
    }

}
