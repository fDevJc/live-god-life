package com.godlife.feeddomain.domain;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.Comment;

import com.godlife.feeddomain.domain.enums.Category;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Feed extends BaseEntity{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Comment("피드 아이디")
	private Long feedId;
	@Enumerated(EnumType.STRING)
	@Comment("피드 카테고리")
	private Category category;
	@Comment("피드 제목")
	private String title;

	//===사용자정보===
	@Comment("작성자 ID")
	private Long userId;

	//===카운팅===
	@Comment("뷰 카운트")
	private Integer viewCount;
	@Comment("가져가기 카운트")
	private Integer pickCount;
	@Comment("투두 카운트")
	private Integer todoCount;
	@Comment("투두 일정")
	private Integer todoScheduleDay;

	//===이미지===
	@Comment("이미지 상세 Path")
	private String image;

	@Comment("이미지 상세 Path")
	private String thumbnailImage;

	private Feed(Long userId, Category category, String title, String image, String thumbnailImage) {
		this.category = category;
		this.title = title;
		this.userId = userId;
		this.image = image;
		this.thumbnailImage = thumbnailImage;
		this.viewCount = 0;
		this.pickCount = 0;
		this.todoCount = 0;
		this.todoScheduleDay = 0;
	}

	public static Feed createFeed(Long userId, Category category, String title, String image, String thumbnailImage) {
		return new Feed(userId, category, title, image, thumbnailImage);
	}

	public void registerTodosInfo(Todos todos) {
		registerTotalTodoTaskCount(todos.getTotalTodoTaskCount());
	}

	private void registerTotalTodoTaskCount(int todoCount) {
		this.todoCount = todoCount;
	}
}
