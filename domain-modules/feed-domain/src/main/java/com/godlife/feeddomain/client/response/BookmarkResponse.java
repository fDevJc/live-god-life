package com.godlife.feeddomain.client.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class BookmarkResponse {
	private String status;
	private List<BookmarkDto> data;
	private Integer code;
	private String message;

	@AllArgsConstructor
	@Data
	public static class BookmarkDto {
		private Long feedId;
		private boolean bookmarkStatus;
	}
}
