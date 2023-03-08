package com.example.websocketredis.controller;

import com.example.websocketredis.config.KafkaDynamicTopic;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController

public class TopicController {
    @Autowired
    private KafkaDynamicTopic kafkaDynamicTopic;
    @PostMapping("createTopic")
    public ResponseEntity<String> createTopic(@RequestParam String topicName){
        //NewTopic newTopic = new NewTopic(topicName, 1, (short) 1);
        kafkaDynamicTopic.createTopicDynamic(topicName);
        return ResponseEntity.ok("Topic " + topicName + " created successfully.");
    }
}
