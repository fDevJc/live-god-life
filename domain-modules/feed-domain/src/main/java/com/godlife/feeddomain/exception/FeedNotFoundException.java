package com.godlife.feeddomain.exception;

import com.godlife.core.exception.CommonException;

public class FeedNotFoundException extends CommonException {
	public FeedNotFoundException(Long feedId) {
		super(String.format("피드아이디 '%s'이 존재하지 않습니다.", feedId));
	}
}
