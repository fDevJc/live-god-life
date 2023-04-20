package com.godlife.feedapi.controller.dto.response;

import lombok.Getter;

@Getter
public enum ResponseCode {
	SUCCESS("success"), ERROR("error");

	private final String lowerName;

	ResponseCode(String lowerName) {
		this.lowerName = lowerName;
	}
}
