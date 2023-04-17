package com.godlife.apicore.dto.response;

public enum ResponseCode {
	SUCCESS("success"), ERROR("error");

	private final String lowerName;

	ResponseCode(String lowerName) {
		this.lowerName = lowerName;
	}

	public String getLowerName() {
		return lowerName;
	}
}
