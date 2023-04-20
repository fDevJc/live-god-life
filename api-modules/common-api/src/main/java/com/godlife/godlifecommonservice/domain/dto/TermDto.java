package com.godlife.godlifecommonservice.domain.dto;

import com.godlife.godlifecommonservice.domain.entity.Term;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder(access = AccessLevel.PRIVATE)
@Getter
@Setter
public class TermDto {

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

    /**
     * 약관 Entity-DTO 변환 메소드
     */
    public static TermDto of(Term term) {
        if (term == null) return null;

        return TermDto.builder()
                .type(term.getType())
                .version(term.getVersion())
                .contents(term.getContents())
                .required(term.isRequired())
                .build();
    }
}
