package com.godlife.feedservice.client.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserResponse {
	private String status;
	private List<UserDto> data;
	private Integer code;
	private String message;

	@AllArgsConstructor
	@Getter
	public static class UserDto {
		private Long userId;
		private String nickname;
		private String image;
	}
}
