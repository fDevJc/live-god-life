package com.godlife.feedapi.dto;

import com.querydsl.core.annotations.QueryProjection;

import lombok.Getter;

@Getter
public class MindsetDto {
	private Long id;
	private String content;

	@QueryProjection
	public MindsetDto(Long id, String content) {
		this.id = id;
		this.content = content;
	}
}
