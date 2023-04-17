package com.godlife.feedapi.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Comment;

@Table(name = "feed_content")
@Entity
public class Content extends BaseEntity{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Comment("피드 컨텐츠 아이디")
	private Long contentId;
	@Comment("피드 컨텐츠 소제목")
	private String title;
	@Comment("피드 컨텐츠 내용")
	@Lob
	private String content;
	@Comment("피드 컨텐츠 정렬순서")
	private Integer orderNumber;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "feed_id")
	private Feed feed;

	private Content(String title, String content, Integer orderNumber, Feed feed) {
		this.title = title;
		this.content = content;
		this.orderNumber = orderNumber;
		this.feed = feed;
	}

	public static Content createContent(String title, String content, Integer orderNumber, Feed feed) {
		return new Content(title, content, orderNumber, feed);
	}
}
