package com.godlife.feedapi.exception;

public abstract class FeedCommonException extends RuntimeException {
	protected FeedCommonException(String message) {
		super(message);
	}
}
