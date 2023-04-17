package com.godlife.feedapi.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Pageable;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.godlife.feedapi.client.UserServiceClient;
import com.godlife.feedapi.client.response.BookmarkResponse;
import com.godlife.feedapi.client.response.UserResponse;
import com.godlife.feedapi.dto.FeedMindsetsTodosDto;
import com.godlife.feedapi.dto.FeedsDto;
import com.godlife.feedapi.exception.NoSuchBookmarkException;
import com.godlife.feedapi.repository.ContentRepository;
import com.godlife.feedapi.repository.FeedRepository;
import com.godlife.feedapi.repository.MindsetRepository;
import com.godlife.feedapi.repository.TodoRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@Transactional(readOnly = true)
@Service
public class FeedService {
	private final FeedRepository feedRepository;
	private final ContentRepository contentRepository;
	private final MindsetRepository mindsetRepository;
	private final TodoRepository todoRepository;

	private final UserServiceClient userServiceClient;

	public List<FeedsDto> getFeeds(Pageable page, Long userId, @Nullable String category, @Nullable List<Long> feedIds) {

		List<FeedsDto> feedsDtos = feedRepository.findAllByCategoryAndFeedIds(page, category, feedIds);
		feedDtosSetUserInfo(feedsDtos, getUsersInfoUsingAPI(getUserIdsToString(feedsDtos)));
		feedDtosSetBookmarkInfo(feedsDtos, getBookmarksInfoUsingAPI(userId, getFeedIdsToString(feedsDtos)));
		return feedsDtos;
	}

	private void feedDtosSetBookmarkInfo(List<FeedsDto> feedsDtos, List<BookmarkResponse.BookmarkDto> bookmarkDtos) {

		bookmarkDtos.forEach(bookmarkDto -> feedsDtos.stream()
			.filter(feedsDto -> feedsDto.getFeedId().equals(bookmarkDto.getFeedId()))
			.findFirst()
			.orElseThrow()
			.registerBookmark(bookmarkDto.isBookmarkStatus()));
	}

	private void feedDtosSetUserInfo(List<FeedsDto> feedsDtos, List<UserResponse.UserDto> userDtos) {

		userDtos.forEach(userDto -> feedsDtos.stream()
			.filter(feedsDto -> feedsDto.getUserId().equals(userDto.getUserId()))
			.collect(Collectors.toList())
			.forEach(feedsDto -> feedsDto.registerUser(userDto)));
	}

	private List<UserResponse.UserDto> getUsersInfoUsingAPI(String ids) {
		try {
			return userServiceClient.getUsers(ids).getData();
		} catch (Exception e) {
			log.error(e.getMessage());
			return List.of();
		}
	}

	private static String getUserIdsToString(List<FeedsDto> feeds) {
		String ids = feeds.stream()
			.map(feed -> feed.getUserId().toString())
			.collect(Collectors.joining(","));
		return ids;
	}

	private List<BookmarkResponse.BookmarkDto> getBookmarksInfoUsingAPI(Long userId, String ids) {
		try {
			return userServiceClient.getBookmarks(userId, ids).getData();
		} catch (Exception e) {
			log.error(e.getMessage());
			return List.of();
		}
	}

	private static String getFeedIdsToString(List<FeedsDto> feeds) {
		return feeds.stream()
			.map(feed -> feed.getFeedId().toString())
			.collect(Collectors.joining(","));
	}

	@Transactional
	public FeedMindsetsTodosDto getFeedDetail(Long userId, Long feedId) {
		FeedMindsetsTodosDto feedMindsetsTodosDto = feedRepository.findFeedWithMindsetsAndTodosByFeedId(feedId);
		feedMindsetsTodosDto.setUserInfo(getUsersInfoUsingAPI(feedMindsetsTodosDto.getUserId().toString()));
		feedMindsetsTodosDto.setBookmarkStatus(
			getBookmarksInfoUsingAPI(userId, feedId.toString())
				.stream()
				.filter(bookmarkDto -> bookmarkDto.getFeedId().equals(feedId))
				.findAny()
				.orElseThrow(() -> new NoSuchBookmarkException(feedId))
				.isBookmarkStatus());
		return feedMindsetsTodosDto;
	}

	// @Transactional
	// public void createFeed(CreateFeedRequest feedDto) {
	//
	// 	Feed feed = feedDto.createFeedEntity();
	// 	List<Content> contents = feedDto.createContentsEntity(feed);
	// 	List<Mindset> mindsets = feedDto.createMindsetsEntity(feed);
	// 	Todos todos = new Todos(feedDto.createTodosEntity(feed));
	//
	// 	feed.registerTodosInfo(todos);
	//
	// 	contentRepository.saveAll(contents);
	// 	mindsetRepository.saveAll(mindsets);
	// 	todoRepository.saveAll(todos.get());
	// }
}
