package com.godlife.feedapi.domain;

import java.util.List;

import javax.persistence.Convert;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Comment;

import com.godlife.feedapi.domain.converter.StringListConverter;
import com.godlife.feedapi.domain.enums.RepetitionType;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("TASK")
@Entity
public class TodoTask extends Todo {
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_todo_id")
	@Comment("부모 투두 아이디")
	private Todo todo;
	@Enumerated(EnumType.STRING)
	@Comment("기간 타입")
	private RepetitionType repetitionType;

	@Convert(converter = StringListConverter.class)
	@Comment("기간 파라미터")
	private List<String> repetitionParams;

	@Comment("피드 투두 시작일")
	private Integer startDay;

	@Comment("피드 투두 종료일")
	private Integer endDay;

	@Comment("알림")
	private String notification;

	private TodoTask(String title, Integer depth, Integer orderNumber, Integer period, RepetitionType repetitionType, List<String> repetitionParams, String notification, Feed feed) {
		super(title, depth, orderNumber, period, feed);
		this.repetitionType = repetitionType;
		this.repetitionParams = repetitionParams;
		this.notification = notification;
	}

	public static Todo createTodoTask(String title, Integer depth, Integer orderNumber,Integer period, RepetitionType repetitionType, List<String> repetitionParams, String notification, Feed feed) {
		return new TodoTask(title, depth, orderNumber, period, repetitionType, repetitionParams, notification, feed);
	}
}
