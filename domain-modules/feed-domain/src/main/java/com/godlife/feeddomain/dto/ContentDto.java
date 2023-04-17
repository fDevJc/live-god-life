package com.godlife.feeddomain.dto;

import com.querydsl.core.annotations.QueryProjection;

import lombok.Getter;

@Getter
public class ContentDto {
	private String title;
	private String content;
	private Integer orderNumber;

	@QueryProjection
	public ContentDto(String title, String content, Integer orderNumber) {
		this.title = title;
		this.content = content;
		this.orderNumber = orderNumber;
	}
}
