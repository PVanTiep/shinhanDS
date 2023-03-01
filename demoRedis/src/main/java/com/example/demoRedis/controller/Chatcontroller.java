package com.example.demoRedis.controller;

import com.example.demoRedis.model.ChatMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;

@Controller
public class Chatcontroller {
    @Autowired
    private RedisTemplate template;
    @Autowired
    private SimpMessageSendingOperations messageTemplate;
    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public ChatMessage sendMessage(@Payload ChatMessage chatMessage){
        template.opsForList().rightPush("List-chat",chatMessage);
        return chatMessage;
    }
    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public ChatMessage addUser(@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor){
        headerAccessor.getSessionAttributes().put("username",chatMessage.getSender());
        List<ChatMessage> arrayList = new ArrayList<>();
        arrayList = template.opsForList().range("List-chat", 0, -1);
        for (ChatMessage chatMessage1:arrayList) {
                chatMessage1.setType(ChatMessage.MessageType.UPDATE);
                messageTemplate.convertAndSend("/topic/public", chatMessage1);

        }
        ChatMessage chatMessage1 = new ChatMessage();
        chatMessage1.setType(ChatMessage.MessageType.CLOSE);
        messageTemplate.convertAndSend("/topic/public",chatMessage1);
        return chatMessage;
    }




}
