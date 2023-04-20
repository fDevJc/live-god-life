package com.godlife.godlifecommonservice.domain.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@Getter
@DiscriminatorValue("PRIVACY")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PrivacyTerm extends Term {

    /**
     * 개인정보 처리방침 생성자
     */
    private PrivacyTerm(String version, String contents, boolean required) {
        super(version, contents, required);
    }

    /**
     * 개인정보 처리방침 객체 생성 메소드
     */
    public static PrivacyTerm of(String version, String contents, boolean required) {
        return new PrivacyTerm(version, contents, required);
    }
}
