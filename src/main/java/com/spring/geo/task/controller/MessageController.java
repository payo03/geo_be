package com.spring.geo.task.controller;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;

import com.spring.geo.common.util.UtilService;
import com.spring.geo.task.model.Message;
import com.spring.geo.task.service.MessageService;

@RestController
public class MessageController {

    @Autowired
    UtilService utilService;

    @Resource(name = "messageService")
    private MessageService messageService;

    @MessageMapping("/receive")
    @SendTo("/send")
    public Message messageHandler(Message message) {
        Message result = messageService.messageHandler(message);

        return result;
    }

}
