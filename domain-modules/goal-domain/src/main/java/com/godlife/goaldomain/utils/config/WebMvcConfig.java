package com.godlife.goaldomain.utils.config;

import com.godlife.goaldomain.utils.converter.LocalDateConverter;
import com.godlife.goaldomain.utils.converter.YearMonthConverter;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

	@Override
	public void addFormatters(FormatterRegistry registry) {
		registry.addConverter(new LocalDateConverter());
		registry.addConverter(new YearMonthConverter());
	}
}
