package com.godlife.feedapi.api.request;

import java.util.List;
import java.util.stream.Collectors;

import com.godlife.feeddomain.domain.Content;
import com.godlife.feeddomain.domain.Feed;
import com.godlife.feeddomain.domain.Mindset;
import com.godlife.feeddomain.domain.Todo;
import com.godlife.feeddomain.domain.TodoFolder;
import com.godlife.feeddomain.domain.TodoTask;
import com.godlife.feeddomain.domain.enums.Category;
import com.godlife.feeddomain.domain.enums.RepetitionType;
import com.godlife.feeddomain.domain.enums.TodoType;

import lombok.Getter;

@Getter
public class CreateFeedRequest {
	private Long userId;

	private String title;
	private String content;
	private String image;
	private String thumbnailImage;

	private String categoryName;
	private String categoryCode;

	private List<CreateFeedContentRequest> contents;
	private List<CreateFeedMindsetRequest> mindsets;
	private List<CreateFeedTodoRequest> todos;

	public Feed createFeedEntity() {
		return Feed.createFeed(userId, Category.valueOf(categoryCode), title, image, thumbnailImage);
	}

	public List<Content> createContentsEntity(Feed feed) {
		return contents.stream()
			.map(createFeedContentRequest -> Content.createContent(createFeedContentRequest.title, createFeedContentRequest.content, createFeedContentRequest.orderNumber, feed))
			.collect(Collectors.toList());
	}

	public List<Mindset> createMindsetsEntity(Feed feed) {
		return mindsets.stream()
			.map(createFeedMindsetRequest -> Mindset.createMindset(createFeedMindsetRequest.getContent(), feed))
			.collect(Collectors.toList());
	}

	public List<Todo> createTodosEntity(Feed feed) {
		return todos.stream()
			.map(todoDto -> createTodoEntity(todoDto, feed))
			.collect(Collectors.toList());
	}

	private Todo createTodoEntity(CreateFeedTodoRequest todoDto, Feed feed) {
		if (TodoType.FOLDER.name().equals(todoDto.getType())) {
			return TodoFolder.createTodoFolder(
				todoDto.getTitle(),
				todoDto.getDepth(),
				todoDto.getOrderNumber(),
				todoDto.getPeriod(),
				todoDto.getTodos()
					.stream()
					.map(createFeedTodoRequest -> createTodoEntity(createFeedTodoRequest, feed))
					.collect(Collectors.toList()),
				feed);
		} else {
			return TodoTask.createTodoTask(
				todoDto.getTitle(),
				todoDto.getDepth(),
				todoDto.getOrderNumber(),
				todoDto.getPeriod(),
				RepetitionType.valueOf(todoDto.getRepetitionType()),
				todoDto.getRepetitionParams(),
				todoDto.getNotification(),
				feed);
		}
	}

	@Getter
	public static class CreateFeedContentRequest {
		private String title;
		private String content;
		private Integer orderNumber;
	}

	@Getter
	public static class CreateFeedMindsetRequest {
		private String content;
	}

	@Getter
	public static class CreateFeedTodoRequest {
		private String title;
		private String type;
		private Integer depth;
		private Integer orderNumber;
		private Integer period;
		private String repetitionType;
		private List<String> repetitionParams;
		private String notification;
		private List<CreateFeedTodoRequest> todos;
	}
}
