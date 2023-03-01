package com.example.demoRedis;

import com.example.demoRedis.model.ChatMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class DemoRedisApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoRedisApplication.class, args);
	}
	@Autowired
	private RedisTemplate template;
	@Bean
	CommandLineRunner commandLineRunner(RedisTemplate<Object,Object> redisTemplate){
		return args -> {
			redisTemplate.opsForValue().set("likelion","hello redis");
			System.out.println("Value of key likelion: "+ redisTemplate.opsForValue().get("likelion"));
			listExample();
		};
	}
	public void listExample(){
		List<String> list = new ArrayList<>();
		list.add("Hello");
		list.add("Redissssssssss");
		template.delete("likelion_list");
		template.delete("List-chat");
		template.opsForList().rightPushAll("likelion_list",list);
		System.out.println("Size of key likelion "+ template.opsForList().size("likelion_list"));
		System.out.println("Size of key List chat "+ template.opsForList().size("List-chat"));
	}

}
