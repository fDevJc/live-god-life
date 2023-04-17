package com.godlife.feedapi.presentation;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.godlife.apicore.dto.response.ApiResponse;
import com.godlife.apicore.dto.response.ResponseCode;
import com.godlife.feedapi.service.FeedQueryService;
import com.godlife.feeddomain.dto.FeedsDto;
import com.godlife.feeddomain.exception.NoSuchFeedException;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@RestController
public class FeedController {
	private final FeedQueryService feedQueryService;
	private static final String USER_ID_HEADER = "x-user";
	private static final int DEFAULT_PAGE = 30;

	@GetMapping("/feeds")
	public ResponseEntity<ApiResponse> getFeeds(
		@PageableDefault(size = DEFAULT_PAGE) Pageable page,
		@RequestHeader(USER_ID_HEADER) Long userId,
		@RequestParam(value = "category", required = false) String category,
		@RequestParam(value = "ids", required = false) List<Long> feedIds) {

		List<FeedsDto> feeds = feedQueryService.getFeeds(page, userId, category, feedIds);

		return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(feeds));
	}

	// @GetMapping("/feeds/{feedId}")
	// public ResponseEntity<ApiResponse> getFeedDetail(
	// 	@RequestHeader(USER_ID_HEADER) Long userId,
	// 	@PathVariable(value = "feedId") Long feedId) {
	//
	// 	FeedMindsetsTodosDto feedDetail = feedService.getFeedDetail(userId, feedId);
	//
	// 	return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(feedDetail));
	// }
	//
	// @GetMapping("/feeds/images/{imageName}")
	// public ResponseEntity<Resource> getImage(
	// 	@PathVariable(value = "imageName") String imageName) {
	//
	// 	try {
	// 		String path = "/home/images/feeds/";
	// 		FileSystemResource resource = new FileSystemResource(path + imageName);
	// 		if (!resource.exists()) {
	// 			throw new NoSuchElementException();
	// 		}
	// 		HttpHeaders header = new HttpHeaders();
	// 		Path filePath = Paths.get(path + imageName);
	// 		header.add("Content-Type", Files.probeContentType(filePath));
	// 		return new ResponseEntity<>(resource, header, HttpStatus.OK);
	// 	} catch (Exception e) {
	// 		throw new NoSuchElementException();
	// 	}
	// }

	/*
		  내부테스트 및 샘플피드생성용 임시 메서드
		  1.0 출시버전에는 피드생성기능없이 출시
	*/
	// @PostMapping("/feeds")
	// public ResponseEntity<ApiResponse> createFeed(
	// 	@RequestBody CreateFeedRequest request) {
	// 	feedService.createFeed(request);
	// 	return null;
	// }

	@ExceptionHandler
	public ResponseEntity<ApiResponse> noSuchFeedException(NoSuchFeedException e) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(ResponseCode.ERROR, e.getMessage()));
	}
}
