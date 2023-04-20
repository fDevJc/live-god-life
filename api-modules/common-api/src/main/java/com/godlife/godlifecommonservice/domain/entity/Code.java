package com.godlife.godlifecommonservice.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Comment;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "codes")
public class Code extends BaseEntity {
	@Id @GeneratedValue
	private Long id;
	@Comment("종류")
	@Column(name = "kinds")
	private String kind;
	@Comment("코드")
	@Column(name = "codes")
	private String code;
	@Comment("값")
	@Column(name = "name")
	private String name;
}
