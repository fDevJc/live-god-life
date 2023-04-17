package com.godlife.feedservice.exception;

import java.util.NoSuchElementException;

public class NoSuchBookmarkException extends NoSuchElementException {
	public NoSuchBookmarkException(Long feedId) {
		super(String.format("피드아이디 '%s'의 북마크정보가 존재하지 않습니다.", feedId));
	}
}
