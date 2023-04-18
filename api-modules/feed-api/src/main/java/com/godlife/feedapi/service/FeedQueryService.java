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
import com.godlife.feeddomain.exception.BookmarkNotFoundException;
import com.godlife.feeddomain.service.FeedFindService;
import com.godlife.feeddomain.service.FeedViewCountService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class FeedQueryService {

	private final FeedFindService feedFindService;
	private final FeedViewCountService feedViewCountService;
	private final UserClientService userClientService;

	public List<FeedDto> getFeeds(Pageable page, Long userId, String category, List<Long> feedIds) {
		List<FeedDto> feedDtos = feedFindService.findFeeds(page, category, feedIds);

		assembleUsersIntoFeeds(feedDtos, getUsersInfoUsingAPI(getUserIdsToString(feedDtos)));
		assembleBookmarksIntoFeeds(feedDtos, getBookmarksInfoUsingAPI(userId, getFeedIdsToString(feedDtos)));

		return feedDtos;
	}

	private void assembleBookmarksIntoFeeds(List<FeedDto> feedDtos, List<BookmarkResponse.BookmarkDto> bookmarkDtos) {
		bookmarkDtos.forEach(bookmarkDto -> feedDtos.stream()
			.filter(feedsDto -> feedsDto.getFeedId().equals(bookmarkDto.getFeedId()))
			.findFirst()
			.orElseThrow()
			.registerBookmark(bookmarkDto.isBookmarkStatus()));
	}

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

	@Transactional
	public FeedMindsetsTodosDto getFeedDetail(Long userId, Long feedId) {
		FeedMindsetsTodosDto feedMindsetsTodosDto = feedFindService.findFeedDetail(feedId);
		feedViewCountService.plusViewCount(feedId);

		UserResponse.UserDto userDto = getUsersInfoUsingAPI(feedMindsetsTodosDto.getUserId().toString()).get(0);
		feedMindsetsTodosDto.setUserInfo(new FeedMindsetsTodosDto.UserDto(userDto.getUserId(), userDto.getNickname(), userDto.getImage()));
		feedMindsetsTodosDto.setBookmarkStatus(
			getBookmarksInfoUsingAPI(userId, feedId.toString())
				.stream()
				.filter(bookmarkDto -> bookmarkDto.getFeedId().equals(feedId))
				.findAny()
				.orElseThrow(() -> new BookmarkNotFoundException(feedId))
				.isBookmarkStatus());

		return feedMindsetsTodosDto;
	}
}
