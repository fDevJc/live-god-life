package com.godlife.feedapi.common;

import java.util.List;

import com.godlife.feedapi.controller.dto.request.CreateFeedRequest;
import com.godlife.feeddomain.enums.Category;
import com.godlife.feeddomain.enums.RepetitionType;
import com.godlife.feeddomain.enums.TodoType;

public class RequestBuilder {

	public static CreateFeedRequest getCreateFeedRequest() {
		CreateFeedRequest.CreateFeedContentRequest createFeedContentRequest = CreateFeedRequest.CreateFeedContentRequest.builder()
			.title("content-title")
			.content("content-content")
			.orderNumber(0)
			.build();

		CreateFeedRequest.CreateFeedMindsetRequest createFeedMindsetRequest = CreateFeedRequest.CreateFeedMindsetRequest.builder()
			.content("mindset-content")
			.build();

		CreateFeedRequest.CreateFeedTodoRequest createFeedTodoRequest = CreateFeedRequest.CreateFeedTodoRequest.builder()
			.title("toto-task")
			.depth(1)
			.notification("0900")
			.orderNumber(0)
			.type(TodoType.TASK.name())
			.period(90)
			.repetitionType(RepetitionType.DAY.name())
			.build();

		return CreateFeedRequest.builder()
			.categoryCode(Category.CAREER.name())
			.image("image")
			.title("feed-title")
			.todos(List.of(createFeedTodoRequest))
			.contents(List.of(createFeedContentRequest))
			.mindsets(List.of(createFeedMindsetRequest))
			.userId(1L)
			.build();
	}

}
