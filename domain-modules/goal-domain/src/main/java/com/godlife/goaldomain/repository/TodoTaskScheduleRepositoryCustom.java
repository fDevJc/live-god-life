package com.godlife.goaldomain.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.godlife.goaldomain.dto.TodoSchedulesDto;

public interface TodoTaskScheduleRepositoryCustom {
	List<TodoSchedulesDto> findAllByTodoId(Pageable page, Long todoId, String criteria);
}
