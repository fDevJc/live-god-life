package com.godlife.feedapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableFeignClients
@EnableJpaRepositories(basePackages = {"com.godlife.feeddomain"})
@EntityScan(basePackages = {"com.godlife.feeddomain"})
@SpringBootApplication(
	scanBasePackages = {"com.godlife.feedapi", "com.godlife.feeddomain"}
)
public class FeedServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(FeedServiceApplication.class, args);
	}

}

