package com.godlife.godlifecommonservice.request;

import lombok.Getter;

@Getter
public class RequestTerm {

    /**
     * 약관 타입
     */
    private String type;

    /**
     * 약관 버전
     */
    private String version;

    /**
     * 약관 내용
     */
    private String contents;

    /**
     * 약관 필수 여부
     */
    private boolean required;
}
