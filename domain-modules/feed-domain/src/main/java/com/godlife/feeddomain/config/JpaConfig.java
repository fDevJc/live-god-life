package com.godlife.feeddomain.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = {"com.godlife.feeddomain"})
@EntityScan(basePackages = {"com.godlife.feeddomain"})
@Configuration
public class JpaConfig {
}
