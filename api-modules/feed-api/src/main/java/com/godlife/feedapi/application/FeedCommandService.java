package com.godlife.feedapi.application;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.godlife.feedapi.presentation.dto.request.CreateFeedRequest;
import com.godlife.feeddomain.domain.Content;
import com.godlife.feeddomain.domain.Feed;
import com.godlife.feeddomain.domain.Mindset;
import com.godlife.feeddomain.domain.Todos;
import com.godlife.feeddomain.service.FeedService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class FeedCommandService {
	private final FeedService feedService;

	@Transactional
	public void createFeed(CreateFeedRequest feedDto) {

		Feed feed = feedDto.createFeedEntity();
		List<Content> contents = feedDto.createContentsEntity(feed);
		List<Mindset> mindsets = feedDto.createMindsetsEntity(feed);
		Todos todos = new Todos(feedDto.createTodosEntity(feed));

		feed.registerTodosInfo(todos);

		feedService.createFeed(contents, mindsets, todos.get());
	}
}
