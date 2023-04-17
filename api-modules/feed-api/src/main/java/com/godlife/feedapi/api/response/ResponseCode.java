package com.godlife.feedapi.api.response;

import lombok.Getter;

@Getter
public enum ResponseCode {
	SUCCESS("success"), ERROR("error");

	private final String lowerName;

	ResponseCode(String lowerName) {
		this.lowerName = lowerName;
	}
}
