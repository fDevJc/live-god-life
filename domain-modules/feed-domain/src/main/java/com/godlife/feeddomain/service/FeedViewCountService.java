package com.godlife.feeddomain.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.godlife.feeddomain.domain.Feed;
import com.godlife.feeddomain.exception.FeedNotFoundException;
import com.godlife.feeddomain.repository.FeedRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class FeedViewCountService {
	private final FeedRepository feedRepository;

	public void plusViewCount(Long feedId) {
		Feed feed = feedRepository.findById(feedId)
			.orElseThrow(() -> new FeedNotFoundException(feedId));

		feed.plusViewCount();
	}
}
