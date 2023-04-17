package com.godlife.godlifecommonservice.domain.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@Getter
@DiscriminatorValue("USE")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UseTerm extends Term {

    /**
     * 이용 약관 생성자
     */
    private UseTerm(String version, String contents, boolean required) {
        super(version, contents, required);
    }

    /**
     * 이용 약관 객체 생성 메소드
     */
    public static UseTerm of(String version, String contents, boolean required) {
        return new UseTerm(version, contents, required);
    }
}
