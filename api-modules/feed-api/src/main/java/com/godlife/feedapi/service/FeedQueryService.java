package com.godlife.feedapi.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.godlife.feedapi.client.UserClientService;
import com.godlife.feedapi.client.response.BookmarkResponse;
import com.godlife.feedapi.client.response.UserResponse;
import com.godlife.feeddomain.dto.FeedDto;
import com.godlife.feeddomain.dto.FeedMindsetsTodosDto;
import com.godlife.feeddomain.service.FeedService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class FeedQueryService {

	private final FeedService feedService;
	private final UserClientService userClientService;

	public List<FeedDto> getFeeds(Pageable page, Long userId, String category, List<Long> feedIds) {
		List<FeedDto> feedDtos = feedService.getFeeds(page, category, feedIds);

		List<UserResponse.UserDto> users = getUsersInfoUsingAPI(getUserIdsToString(feedDtos));
		List<BookmarkResponse.BookmarkDto> bookmarks = getBookmarksInfoUsingAPI(userId, getFeedIdsToString(feedDtos));

		assembleUsersIntoFeeds(feedDtos, users);
		assembleBookmarksIntoFeeds(feedDtos, bookmarks);

		return feedDtos;
	}

	private void assembleBookmarksIntoFeeds(List<FeedDto> feedDtos, List<BookmarkResponse.BookmarkDto> bookmarkDtos) {
		bookmarkDtos.forEach(bookmarkDto -> feedDtos.stream()
			.filter(feedsDto -> feedsDto.getFeedId().equals(bookmarkDto.getFeedId()))
			.findFirst()
			.orElseThrow()
			.registerBookmark(bookmarkDto.isBookmarkStatus()));
	}

	//TODO feedDto 정리
	private void assembleUsersIntoFeeds(List<FeedDto> feedDtos, List<UserResponse.UserDto> userDtos) {
		userDtos.forEach(userDto -> feedDtos.stream()
			.filter(feedsDto -> feedsDto.getUserId().equals(userDto.getUserId()))
			.collect(Collectors.toList())
			.forEach(feedsDto -> feedsDto.registerUser(new FeedDto.UserDto(userDto.getUserId(), userDto.getNickname(), userDto.getImage()))));
	}

	private List<UserResponse.UserDto> getUsersInfoUsingAPI(String ids) {
		try {
			return userClientService.getUsers(ids).getData();
		} catch (Exception e) {
			log.error(e.getMessage());
			return List.of();
		}
	}

	private static String getUserIdsToString(List<FeedDto> feeds) {
		String ids = feeds.stream()
			.map(feed -> feed.getUserId().toString())
			.collect(Collectors.joining(","));
		return ids;
	}

	private List<BookmarkResponse.BookmarkDto> getBookmarksInfoUsingAPI(Long userId, String ids) {
		try {
			return userClientService.getBookmarks(userId, ids).getData();
		} catch (Exception e) {
			log.error(e.getMessage());
			return List.of();
		}
	}

	private static String getFeedIdsToString(List<FeedDto> feeds) {
		return feeds.stream()
			.map(feed -> feed.getFeedId().toString())
			.collect(Collectors.joining(","));
	}

	public FeedMindsetsTodosDto getFeedDetail(Long userId, Long feedId) {
		FeedMindsetsTodosDto feedMindsetsTodosDto = feedService.getFeedDetail(feedId);
		// TODO dto 리팩토링 대상
		// feedMindsetsTodosDto.setUserInfo(getUsersInfoUsingAPI(feedMindsetsTodosDto.getUserId().toString()));
		// feedMindsetsTodosDto.setBookmarkStatus(
		// 	getBookmarksInfoUsingAPI(userId, feedId.toString())
		// 		.stream()
		// 		.filter(bookmarkDto -> bookmarkDto.getFeedId().equals(feedId))
		// 		.findAny()
		// 		.orElseThrow(() -> new NoSuchBookmarkException(feedId))
		// 		.isBookmarkStatus());
		return feedMindsetsTodosDto;
	}
}
