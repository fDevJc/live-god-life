package com.godlife.goalapi.api;

import java.time.LocalDate;
import java.time.YearMonth;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.godlife.goaldomain.dto.request.CreateGoalRequest;
import com.godlife.goaldomain.dto.request.UpdateGoalTodoScheduleRequest;
import com.godlife.goaldomain.dto.response.ApiResponse;
import com.godlife.goaldomain.service.GoalCommandService;
import com.godlife.goaldomain.service.GoalQueryService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
public class GoalController {
	private final GoalCommandService goalCommandService;
	private final GoalQueryService goalQueryService;
	private static final int DEFAULT_PAGE = 25;
	private static final String USER_ID_HEADER = "x-user";

	@PostMapping("/goals")
	public ResponseEntity<ApiResponse> createGoal(
		@RequestHeader(USER_ID_HEADER) Long userId,
		@RequestBody CreateGoalRequest request) {

		goalCommandService.createGoal(userId, request);

		return ResponseEntity
			.status(HttpStatus.CREATED)
			.body(ApiResponse.createPostSuccessResponse());
	}

	@PatchMapping("/goals/todoSchedules/{todoScheduleId}")
	public ResponseEntity<ApiResponse> patchCompletionStatus(
		@RequestHeader(USER_ID_HEADER) Long userId,
		@PathVariable(value = "todoScheduleId") Long todoScheduleId,
		@RequestBody UpdateGoalTodoScheduleRequest request) {

		goalCommandService.updateTodoScheduleCompletionStatus(userId, todoScheduleId, request.getCompletionStatus());

		return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.createPatchSuccessResponse());
	}

	@DeleteMapping("/goals/todos/{todoId}")
	public ResponseEntity<ApiResponse> deleteTodoDetail(
		@RequestHeader(USER_ID_HEADER) Long userId,
		@PathVariable(value = "todoId") Long todoId) {

		goalCommandService.deleteTodo(userId, todoId);

		return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.createDeleteSuccessResponse());
	}

	@DeleteMapping("/goals/{goalId}")
	public ResponseEntity<ApiResponse> deleteGoal(
		@RequestHeader(USER_ID_HEADER) Long userId,
		@PathVariable(value = "goalId") Long goalId) {

		goalCommandService.deleteGoal(userId, goalId);

		return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.createDeleteSuccessResponse());
	}

	@PutMapping("/goals/{goalId}")
	public ResponseEntity<ApiResponse> modifyGoal(
		@RequestHeader(USER_ID_HEADER) Long userId,
		@PathVariable(value = "goalId") Long goalId,
		@RequestBody CreateGoalRequest request) {

		goalCommandService.modifyGoal(userId, goalId, request);

		return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.createPutSuccessResponse());
	}

	@GetMapping("/goals")
	public ResponseEntity<ApiResponse> getGoals(
		@PageableDefault(size = DEFAULT_PAGE) Pageable page,
		@RequestHeader(USER_ID_HEADER) Long userId,
		@RequestParam(required = false) Boolean completionStatus) {

		return ResponseEntity
			.status(HttpStatus.OK)
			.body(ApiResponse.createGetSuccessResponse(goalQueryService.getGoals(page, userId, completionStatus)));
	}

	@GetMapping("/goals/{goalId}")
	public ResponseEntity<ApiResponse> getGoalDetail(
		@RequestHeader(USER_ID_HEADER) Long userId,
		@PathVariable(value = "goalId") Long goalId) {

		return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.createGetSuccessResponse(goalQueryService.getGoalDetail(userId, goalId)));
	}

	@GetMapping("/goals/mindsets")
	public ResponseEntity<ApiResponse> getGoalsWithMindsets(
		@PageableDefault(size = DEFAULT_PAGE) Pageable page,
		@RequestHeader(USER_ID_HEADER) Long userId,
		@RequestParam(required = false) Boolean completionStatus) {

		return ResponseEntity.status(HttpStatus.OK)
			.body(ApiResponse.createGetSuccessResponse(goalQueryService.getGoalsWithMindsets(page, userId, completionStatus)));
	}

	@GetMapping("/goals/todos/counts")
	public ResponseEntity<ApiResponse> getDailyTodosCount(
		@RequestHeader(USER_ID_HEADER) Long userId,
		@RequestParam(value = "date") YearMonth date) {

		return ResponseEntity.status(HttpStatus.OK)
			.body(ApiResponse.createGetSuccessResponse(goalQueryService.getDailyTodosCount(userId, date)));
	}

	@GetMapping("/goals/todos")
	public ResponseEntity<ApiResponse> getDailyGoalsAndTodos(
		@PageableDefault(size = DEFAULT_PAGE) Pageable page,
		@RequestHeader(USER_ID_HEADER) Long userId,
		@RequestParam(value = "date") LocalDate searchedDate,
		@RequestParam(value = "completionStatus", required = false) Boolean completionStatus) {

		return ResponseEntity.status(HttpStatus.OK)
			.body(ApiResponse.createGetSuccessResponse(goalQueryService.getDailyGoalsAndTodos(page, userId, searchedDate, completionStatus)));
	}

	@GetMapping("/goals/todos/{todoId}")
	public ResponseEntity<ApiResponse> getTodoDetail(
		@RequestHeader(USER_ID_HEADER) Long userId,
		@PathVariable(value = "todoId") Long todoId) {

		return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.createGetSuccessResponse(goalQueryService.getTodoDetail(userId, todoId)));
	}

	@GetMapping("/goals/todos/{todoId}/todoSchedules")
	public ResponseEntity<ApiResponse> getTodoSchedules(
		@PageableDefault(size = DEFAULT_PAGE) Pageable page,
		@RequestHeader(USER_ID_HEADER) Long userId,
		@PathVariable(value = "todoId") Long todoId,
		@RequestParam(value = "criteria", required = false, defaultValue = "after") String criteria) {

		return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.createGetSuccessResponse(goalQueryService.getTodoSchedules(page, userId, todoId, criteria)));
	}
}
