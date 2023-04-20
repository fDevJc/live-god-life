package com.godlife.godlifecommonservice.domain.entity.image;

import com.godlife.godlifecommonservice.domain.entity.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor
public class Image extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("이미지 ID")
    private Long imageId;

    @Column(length = 500)
    @Comment("이미지 URL")
    private String url;

    @Builder
    public Image(String url) {
        this.url = url;
    }
}
