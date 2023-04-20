package com.godlife.godlifecommonservice.domain.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder(access = AccessLevel.PRIVATE)
public class ImageDto {

    /**
     * 이미지 URL
     */
    private String url;

    /**
     * 이미지 DTO 객체 생성 메소드
     */
    public static ImageDto of(String url) {
        return ImageDto.builder()
                .url(url)
                .build();
    }
}
