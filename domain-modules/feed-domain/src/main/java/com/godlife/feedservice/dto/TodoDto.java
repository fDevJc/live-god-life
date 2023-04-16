package com.godlife.feedservice.dto;

import java.util.List;

// import com.fasterxml.jackson.annotation.JsonIgnore;
// import com.fasterxml.jackson.annotation.JsonInclude;
import com.querydsl.core.annotations.QueryProjection;

import lombok.Getter;

@Getter
// @JsonInclude(JsonInclude.Include.NON_NULL)
public class TodoDto {
	//===투두 공통===
	private Long todoId;
	// @JsonIgnore
	private Long parentTodoId;
	private String type;
	private String title;
	private Integer depth;
	private Integer orderNumber;
	private Integer period;
	private Integer startDay;
	private Integer endDay;

	//===투두 타스크===
	private String repetitionType;
	private List<String> repetitionParams;
	private String notification;

	//===투두 폴더===
	private List<TodoDto> childTodos;

	@QueryProjection
	public TodoDto(Long todoId, String type, String title, Integer depth, Integer orderNumber, Integer period, Integer startDay, Integer endDay, String repetitionType, List<String> repetitionParams,
		String notification) {

		this.todoId = todoId;
		this.type = type;
		this.title = title;
		this.depth = depth;
		this.orderNumber = orderNumber;
		this.period = period;
		this.startDay = startDay;
		this.endDay = endDay;
		this.repetitionType = repetitionType;
		this.repetitionParams = repetitionParams;
		this.notification = notification;
	}

	@QueryProjection
	public TodoDto(Long todoId, Long parentTodoId, String type, String title, Integer depth, Integer orderNumber, Integer period, Integer startDay, Integer endDay, String repetitionType,
		List<String> repetitionParams, String notification) {

		this.todoId = todoId;
		this.parentTodoId = parentTodoId;
		this.type = type;
		this.title = title;
		this.depth = depth;
		this.orderNumber = orderNumber;
		this.period = period;
		this.startDay = startDay;
		this.endDay = endDay;
		this.repetitionType = repetitionType;
		this.repetitionParams = repetitionParams;
		this.notification = notification;
	}

	public void registerChildTodos(List<TodoDto> todoDtos) {
		this.childTodos = todoDtos;
	}
}
