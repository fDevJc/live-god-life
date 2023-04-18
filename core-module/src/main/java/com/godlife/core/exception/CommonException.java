package com.godlife.core.exception;

public abstract class CommonException extends RuntimeException {
	protected CommonException(String message) {
		super(message);
	}
}
