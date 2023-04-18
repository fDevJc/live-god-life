package com.godlife.feeddomain.service;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.godlife.feeddomain.domain.Content;
import com.godlife.feeddomain.domain.Mindset;
import com.godlife.feeddomain.domain.Todo;
import com.godlife.feeddomain.dto.FeedMindsetsTodosDto;
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

	public List<FeedsDto> getFeeds(Pageable page, Long userId, @Nullable String category, @Nullable List<Long> feedIds) {
		List<FeedsDto> feedsDtos = feedRepository.findAllByCategoryAndFeedIds(page, category, feedIds);
		return feedsDtos;
	}

	/*
		TODO
		조회수 증가 로직 분리하기
	 */
	@Transactional
	public FeedMindsetsTodosDto getFeedDetail(Long userId, Long feedId) {
		FeedMindsetsTodosDto feedMindsetsTodosDto = feedRepository.findFeedWithMindsetsAndTodosByFeedId(feedId);
		return feedMindsetsTodosDto;
	}

	//TODO dto 정리
	@Transactional
	public void createFeed(List<Content> contents, List<Mindset> mindsets, List<Todo> todos) {
		System.out.println("FeedService.createFeed");
		contentRepository.saveAll(contents);
		mindsetRepository.saveAll(mindsets);
		todoRepository.saveAll(todos);
	}
}
