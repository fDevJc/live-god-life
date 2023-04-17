package com.godlife.feedapi.exception;

import java.util.NoSuchElementException;

public class NoSuchFeedException extends NoSuchElementException {
	public NoSuchFeedException(Long feedId) {
		super(String.format("피드아이디 '%s'이 존재하지 않습니다.", feedId));
	}
}
