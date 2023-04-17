package com.godlife.feedapi.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Comment;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorColumn(name = "type")
@Entity
@Table(name = "feed_todo")
public abstract class Todo extends BaseEntity{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Comment("투두 아이디")
	private Long todoId;

	@Column(insertable = false, updatable = false)
	@Comment("투두 타입")
	private String type;

	@Column(nullable = false)
	@Comment("투두 제목")
	private String title;

	@Column(nullable = false)
	@Comment("투두 뎁스")
	private Integer depth;

	@Column(nullable = false)
	@Comment("투두 정렬순서")
	private Integer orderNumber;

	@Comment("투두 기간")
	private Integer period;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "feed_id")
	@Comment("피드 아이디")
	private Feed feed;

	public Todo(String title, Integer depth, Integer orderNumber, Integer period, Feed feed) {
		this.title = title;
		this.depth = depth;
		this.orderNumber = orderNumber;
		this.period = period;
		this.feed = feed;
	}
}
