package com.godlife.feeddomain.service;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.godlife.feeddomain.dto.FeedMindsetsTodosDto;
import com.godlife.feeddomain.dto.FeedDto;
import com.godlife.feeddomain.repository.FeedRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@Transactional(readOnly = true)
@Service
public class FeedFindService {
	private final FeedRepository feedRepository;

	public List<FeedDto> findFeeds(Pageable page, @Nullable String category, @Nullable List<Long> feedIds) {
		return feedRepository.findAllByCategoryAndFeedIds(page, category, feedIds);
	}

	public FeedMindsetsTodosDto findFeedDetail(Long feedId) {
		return feedRepository.findFeedWithMindsetsAndTodosByFeedId(feedId);
	}
}
