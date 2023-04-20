package com.godlife.feedapi.controller.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ApiResponse<T> {
	private String status;
	private String message;
	private T data;

	public ApiResponse(T data) {
		this.status = ResponseCode.SUCCESS.getLowerName();
		this.data = data;
	}

	public ApiResponse(ResponseCode responseCode, String message) {
		this.status = responseCode.getLowerName();
		this.message = message;
	}
}
