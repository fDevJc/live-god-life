package com.godlife.feedapi.application;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.godlife.feedapi.client.UserServiceClient;
import com.godlife.feedapi.client.response.BookmarkResponse;
import com.godlife.feedapi.client.response.UserResponse;
import com.godlife.feeddomain.dto.FeedsDto;
import com.godlife.feeddomain.service.FeedService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class FeedQueryService {
	private final FeedService feedService;
	private final UserServiceClient userServiceClient;

	public List<FeedsDto> getFeeds(Pageable page, Long userId, String category, List<Long> feedIds) {
		List<FeedsDto> feedsDtos = feedService.getFeeds(page, userId, category, feedIds);
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

	//TODO feedDto 정리
	private void feedDtosSetUserInfo(List<FeedsDto> feedsDtos, List<UserResponse.UserDto> userDtos) {
		//
		// userDtos.forEach(userDto -> feedsDtos.stream()
		// 	.filter(feedsDto -> feedsDto.getUserId().equals(userDto.getUserId()))
		// 	.collect(Collectors.toList())
		// 	.forEach(feedsDto -> feedsDto.registerUser(userDto)));
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
}
