package com.godlife.goaldomain.utils;

import com.godlife.goaldomain.dto.request.CreateGoalTodoRequest;

import java.util.List;

public class SampleTestDataCreator {
	public static CreateGoalTodoRequest getCreateGoalTodoTaskRequest(String title, Integer depth, Integer orderNumber,
		String startDate, String endDate, String repetitionType, List<String> repetitionParams, String notification) {
		return CreateGoalTodoRequest.builder()
			.type("TASK")
			.title(title)
			.depth(depth)
			.orderNumber(orderNumber)
			.startDate(startDate)
			.endDate(endDate)
			.repetitionType(repetitionType)
			.repetitionParams(repetitionParams)
			.notification(notification)
			.build();
	}

	public static CreateGoalTodoRequest getCreateGoalTodoFolderRequest(String title, Integer depth, Integer orderNumber,
		List<CreateGoalTodoRequest> childTodoRequest) {
		return CreateGoalTodoRequest.builder()
			.type("FOLDER")
			.depth(depth)
			.orderNumber(orderNumber)
			.title(title)
			.todos(childTodoRequest)
			.build();
	}
}
