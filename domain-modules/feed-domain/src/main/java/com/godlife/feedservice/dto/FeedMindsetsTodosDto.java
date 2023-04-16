package com.godlife.feedservice.dto;

import java.util.List;

import com.godlife.feedservice.client.response.UserResponse;
import com.godlife.feedservice.domain.enums.Category;
import com.querydsl.core.annotations.QueryProjection;

import lombok.Getter;

@Getter
// @JsonInclude(JsonInclude.Include.NON_NULL)
public class FeedMindsetsTodosDto {
	//===피드 정보===
	private Long feedId;
	private Category category;
	private String title;

	//===사용자정보===
	// @JsonIgnore
	private Long userId;
	private UserResponse.UserDto user;

	private Boolean bookMarkStatus;

	//===카운팅===
	private int viewCount;
	private int pickCount;
	private int todoCount;
	private int todoScheduleDay;

	//===이미지===
	private String image;

	//===컨텐츠===
	private List<ContentDto> contents;

	//===마인드셋 정보===
	private List<MindsetDto> mindsets;

	//===투두 정보===
	private List<TodoDto> todos;

	@QueryProjection
	public FeedMindsetsTodosDto(Long feedId, Category category, String title, Long userId, int viewCount, int pickCount, int todoCount, int todoScheduleDay, String image) {
		this.feedId = feedId;
		this.category = category;
		this.title = title;
		this.userId = userId;
		this.viewCount = viewCount;
		this.pickCount = pickCount;
		this.todoCount = todoCount;
		this.todoScheduleDay = todoScheduleDay;
		this.image = image;
	}

	public void registerContentDtos(List<ContentDto> contentDtos) {
		this.contents = contentDtos;
	}

	public void registerMindsetDtos(List<MindsetDto> mindsetDtos) {
		this.mindsets = mindsetDtos;
	}

	public void registerTodoDtos(List<TodoDto> todoDtos) {
		this.todos = todoDtos;
	}

	public void setBookmarkStatus(boolean bookMarkStatus) {
		this.bookMarkStatus = bookMarkStatus;
	}

	public void setUserInfo(List<UserResponse.UserDto> user) {
		this.user = user.get(0);
	}
}
