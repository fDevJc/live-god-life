package com.godlife.feeddomain.exception;

import com.godlife.core.exception.CommonException;

public class BookmarkNotFoundException extends CommonException {
	public BookmarkNotFoundException(Long feedId) {
		super(String.format("피드아이디 '%s'의 북마크정보가 존재하지 않습니다.", feedId));
	}
}
