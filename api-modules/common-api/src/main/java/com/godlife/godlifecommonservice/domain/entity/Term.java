package com.godlife.godlifecommonservice.domain.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Getter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class Term extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("약관 아이디")
    private Long termId;

    @Column(name = "type", insertable = false, updatable = false)
    @Comment("약관 타입")
    protected String type;

    @Comment("약관 버전")
    private String version;

    @Comment("약관 내용")
    private String contents;

    @Comment("약관 필수 여부")
    private boolean required;

    /**
     * Term 생성자
     */
    protected Term(String version, String contents, boolean required) {
        this.version = version;
        this.contents = contents;
        this.required = required;
    }
}
