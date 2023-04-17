package com.godlife.godlifecommonservice.domain.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@Getter
@DiscriminatorValue("MARKETING")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MarketingTerm extends Term {

    /**
     * 마케팅 정보 수신 동의 생성자
     */
    private MarketingTerm(String version, String contents, boolean required) {
        super(version, contents, required);
    }

    /**
     * 마케팅 정보 수신 동의 객체 생성 메소드
     */
    public static MarketingTerm of(String version, String contents, boolean required) {
        return new MarketingTerm(version, contents, required);
    }
}
