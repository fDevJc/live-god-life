package com.godlife.feeddomain.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import com.querydsl.core.annotations.QueryProjection;

import lombok.AllArgsConstructor;
import lombok.Getter;


//TODO dto 정리
@Getter
public class FeedDto {
	private Long feedId;
	private String title;
	private String category;

	//===카운팅===
	private int viewCount;
	private int pickCount;
	private int todoCount;
	private int todoScheduleDay;

	//===이미지===
	private String image;

	//===북마크정보===
	private Boolean bookMarkStatus;

	//===사용자정보===
	@JsonIgnore
	private Long userId;
	private UserDto user;

	@QueryProjection
	public FeedDto(Long feedId, String title, String category, int viewCount, int pickCount, int todoCount, int todoScheduleDay, String image, Long userId) {
		this.feedId = feedId;
		this.title = title;
		this.category = category;
		this.viewCount = viewCount;
		this.pickCount = pickCount;
		this.todoCount = todoCount;
		this.todoScheduleDay = todoScheduleDay;
		this.image = image;
		this.userId = userId;
	}

	public void registerUser(UserDto userDto) {
		this.user = userDto;
	}

	public void registerBookmark(boolean bookmarkStatus) {
		this.bookMarkStatus = bookmarkStatus;
	}

	@AllArgsConstructor
	@Getter
	public static class UserDto {
		private Long userId;
		private String nickname;
		private String image;
	}
}
