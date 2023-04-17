package com.godlife.feedapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication(
	scanBasePackages = {"com.jc.feedapi", "com.jc.feeddomain"}
)
public class FeedServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(FeedServiceApplication.class, args);
	}

}
