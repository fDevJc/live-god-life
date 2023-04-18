package com.godlife.feedapi.controller.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ApiResponse {
	private String status;
	private String message;
	private Object data;

	public ApiResponse(Object data) {
		this.status = ResponseCode.SUCCESS.getLowerName();
		this.data = data;
	}

	public ApiResponse(ResponseCode responseCode, String message) {
		this.status = responseCode.getLowerName();
		this.message = message;
	}
}
