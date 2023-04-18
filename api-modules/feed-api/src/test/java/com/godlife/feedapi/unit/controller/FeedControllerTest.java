package com.godlife.feedapi.unit.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.godlife.feedapi.service.FeedCommandService;
import com.godlife.feedapi.service.FeedQueryService;
import com.godlife.feedapi.client.UserClientService;
import com.godlife.feedapi.client.response.BookmarkResponse;
import com.godlife.feedapi.client.response.UserResponse;
import com.godlife.feedapi.controller.FeedController;

import com.godlife.feeddomain.dto.FeedsDto;
import com.godlife.feeddomain.service.FeedService;

@WebMvcTest({FeedController.class})
public class FeedControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	UserClientService userClientService;

	@MockBean
	FeedService feedService;

	@MockBean
	FeedQueryService feedQueryService;

	@MockBean
	FeedCommandService feedCommandService;

	@Test
	@DisplayName("모든 피드내용을 조회한다_카테고리 전체일경우")
	void getFeedAll() throws Exception {
		//when
		when(feedQueryService.getFeeds(any(), any(), any(), any()))
			.thenReturn(List.of(new FeedsDto(1L, "타이틀", "카테고리", 0, 0, 0, 0, "image", 1L)));

		//then
		mockMvc.perform(get("/feeds")
				.header("x-user", "1")
				.queryParam("page", "0")
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.data.[0].feedId").exists())
			.andDo(print());
	}

	@Test
	@DisplayName("모든 피드내용을 조회한다_카테고리 커리어 CAREER")
	void getFeedsByCategory() throws Exception {
		//when
		when(userClientService.getUsers(any(String.class)))
			.thenReturn(new UserResponse("success",
				List.of(new UserResponse.UserDto(1L, "yang", "image")),
				200,
				"message"));

		when(userClientService.getBookmarks(any(Long.class), any(String.class)))
			.thenReturn(new BookmarkResponse("success",
				List.of(new BookmarkResponse.BookmarkDto(1L, false)),
				200,
				"message"));

		//then
		mockMvc.perform(get("/feeds")
				.header("x-user", "1")
				.queryParam("page", "1")
				.queryParam("category", "CAREER")
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andDo(print());
	}

	@Test
	@DisplayName("모든 피드내용을 조회한다_파라미터 아이디배열에따라")
	void getFeedByIds() throws Exception {
		//when
		when(userClientService.getUsers(any(String.class)))
			.thenReturn(new UserResponse("success",
				List.of(new UserResponse.UserDto(1L, "yang", "image")),
				200,
				"message"));

		when(userClientService.getBookmarks(any(Long.class), any(String.class)))
			.thenReturn(new BookmarkResponse("success",
				List.of(new BookmarkResponse.BookmarkDto(1L, false)),
				200,
				"message"));

		//then
		mockMvc.perform(get("/feeds")
				.header("x-user", "1")
				.queryParam("ids", "1,2,3")
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andDo(print());
	}

	@Test
	@DisplayName("피드 상세내용을 조회한다.")
	void getFeedDetail() throws Exception {
		mockMvc.perform(get("/feeds/{feedId}", 1)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andDo(print());
	}
}
