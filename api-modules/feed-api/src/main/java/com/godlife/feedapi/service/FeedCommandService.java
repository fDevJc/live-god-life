package com.godlife.feedapi.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.godlife.feedapi.controller.dto.request.CreateFeedRequest;
import com.godlife.feeddomain.domain.Content;
import com.godlife.feeddomain.domain.Feed;
import com.godlife.feeddomain.domain.Mindset;
import com.godlife.feeddomain.domain.Todos;
import com.godlife.feeddomain.service.FeedCreateService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class FeedCommandService {
	private final FeedCreateService feedCreateService;

	@Transactional
	public void createFeed(CreateFeedRequest feedDto) {

		Feed feed = feedDto.createFeedEntity();
		List<Content> contents = feedDto.createContentsEntity(feed);
		List<Mindset> mindsets = feedDto.createMindsetsEntity(feed);
		Todos todos = new Todos(feedDto.createTodosEntity(feed));

		feed.registerTodosInfo(todos);

		feedCreateService.createFeed(contents, mindsets, todos.get());
	}
}
