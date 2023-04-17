package com.godlife.feeddomain.service;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.godlife.feeddomain.dto.FeedsDto;
import com.godlife.feeddomain.repository.ContentRepository;
import com.godlife.feeddomain.repository.FeedRepository;
import com.godlife.feeddomain.repository.MindsetRepository;
import com.godlife.feeddomain.repository.TodoRepository;

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

	public List<FeedsDto> getFeeds(Pageable page, String category, List<Long> feedIds) {
		return feedRepository.findAllByCategoryAndFeedIds(page, category, feedIds);
	}

	// @Transactional
	// public FeedMindsetsTodosDto getFeedDetail(Long userId, Long feedId) {
	// 	FeedMindsetsTodosDto feedMindsetsTodosDto = feedRepository.findFeedWithMindsetsAndTodosByFeedId(feedId);
	// 	feedMindsetsTodosDto.setUserInfo(getUsersInfoUsingAPI(feedMindsetsTodosDto.getUserId().toString()));
	// 	feedMindsetsTodosDto.setBookmarkStatus(
	// 		getBookmarksInfoUsingAPI(userId, feedId.toString())
	// 			.stream()
	// 			.filter(bookmarkDto -> bookmarkDto.getFeedId().equals(feedId))
	// 			.findAny()
	// 			.orElseThrow(()-> new NoSuchBookmarkException(feedId))
	// 			.isBookmarkStatus());
	// 	return feedMindsetsTodosDto;
	// }

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
