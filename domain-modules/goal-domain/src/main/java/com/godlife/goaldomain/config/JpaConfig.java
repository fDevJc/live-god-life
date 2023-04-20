package com.godlife.goaldomain.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = {"com.godlife.goaldomain"})
@EntityScan(basePackages = {"com.godlife.goaldomain"})
@Configuration
public class JpaConfig {
}
