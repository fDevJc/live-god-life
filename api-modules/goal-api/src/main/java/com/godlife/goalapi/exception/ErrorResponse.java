package com.godlife.goalapi.exception;

public class ErrorResponse {
	private final String status = "error";
	private String message;

	public ErrorResponse(String message) {
		this.message = message;
	}
}
