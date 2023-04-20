package com.godlife.goaldomain.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.godlife.goaldomain.domain.Goal;
import com.godlife.goaldomain.domain.Mindset;
import com.godlife.goaldomain.domain.Todo;
import com.godlife.goaldomain.domain.TodoTask;
import com.godlife.goaldomain.domain.TodoTaskSchedule;
import com.godlife.goaldomain.domain.Todos;
import com.godlife.goaldomain.domain.enums.Category;
import com.godlife.goaldomain.dto.request.CreateGoalRequest;
import com.godlife.goaldomain.exception.NoSuchGoalException;
import com.godlife.goaldomain.exception.NoSuchTodoException;
import com.godlife.goaldomain.exception.NoSuchTodoScheduleException;
import com.godlife.goaldomain.repository.GoalRepository;
import com.godlife.goaldomain.repository.MindsetRepository;
import com.godlife.goaldomain.repository.TodoRepository;
import com.godlife.goaldomain.repository.TodoTaskScheduleRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class GoalCommandService {
	private final GoalRepository goalRepository;
	private final MindsetRepository mindsetRepository;
	private final TodoRepository todoRepository;
	private final TodoTaskScheduleRepository todoTaskScheduleRepository;

	public void createGoal(Long userId, CreateGoalRequest goalMindsetsTodosDto) {
		Goal goal = goalMindsetsTodosDto.createGoalEntity(userId);
		List<Mindset> mindsets = goalMindsetsTodosDto.createMindsetsEntity(goal);
		Todos todos = new Todos(goalMindsetsTodosDto.createTodosEntity(goal));

		goal.registerTodosInfo(todos);

		mindsetRepository.saveAll(mindsets);
		todoRepository.saveAll(todos.get());
	}

	public void updateTodoScheduleCompletionStatus(Long userId, Long todoScheduleId, Boolean completionStatus) {
		TodoTaskSchedule schedule = todoTaskScheduleRepository.findById(todoScheduleId)
			.orElseThrow(NoSuchTodoScheduleException::new);

		if (completionStatus) {
			schedule.updateCompletionStatus();
		} else {
			schedule.updateInCompletionStatus();
		}
	}

	public void deleteTodo(Long userId, Long todoId) {
		Todo todo = todoRepository.findById(todoId).orElseThrow(NoSuchTodoException::new);
		List<TodoTaskSchedule> todoTaskSchedules = todoTaskScheduleRepository.findAllByTodoTaskTodoId(todoId);
		todoTaskScheduleRepository.deleteAll(todoTaskSchedules);
		todoRepository.delete(todo);
	}

	public void deleteGoal(Long userId, Long goalId) {
		Goal goal = goalRepository.findById(goalId).orElseThrow(NoSuchGoalException::new);
		deleteMindsets(goal);
		deleteTodos(goal);
		goalRepository.delete(goal);
	}

	private void deleteTodos(Goal goal) {
		List<Todo> todos = todoRepository.findAllByGoal(goal);
		todos.stream().filter((todo -> todo instanceof TodoTask)).forEach(todoTask -> todoTaskScheduleRepository.deleteByTodoTask((TodoTask)todoTask));
		todos.forEach(todoRepository::delete);
	}

	private void deleteMindsets(Goal goal) {
		goal.initMindSetCount();
		List<Mindset> mindsets = mindsetRepository.findAllByGoal(goal);
		mindsets.forEach(mindsetRepository::delete);
	}

	public void modifyGoal(Long userId, Long goalId, CreateGoalRequest request) {
		Goal goal = goalRepository.findById(goalId).orElseThrow(NoSuchGoalException::new);

		goal.changeTitle(request.getTitle());
		goal.changeCategory(Category.valueOf(request.getCategoryCode()));

		deleteMindsets(goal);
		deleteTodos(goal);
		List<Mindset> mindsets = request.createMindsetsEntity(goal);
		Todos todos = new Todos(request.createTodosEntity(goal));
		goal.registerTodosInfo(todos);
		mindsetRepository.saveAll(mindsets);
		todoRepository.saveAll(todos.get());
	}
}
