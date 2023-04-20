package com.godlife.goalapi.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.godlife.goaldomain.dto.response.ApiResponse;
import com.godlife.goaldomain.exception.NoSuchGoalException;
import com.godlife.goaldomain.exception.NoSuchTodoException;
import com.godlife.goaldomain.exception.NoSuchTodosInTodoException;

@RestControllerAdvice
public class GoalControllerAdvice {

	@ExceptionHandler
	public ResponseEntity<ApiResponse> noSuchTodosInTodoException(NoSuchTodosInTodoException e) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.createErrorResponse(e.getMessage()));
	}

	@ExceptionHandler
	public ResponseEntity<ApiResponse> noSuchTodoException(NoSuchTodoException e) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.createErrorResponse(e.getMessage()));
	}

	@ExceptionHandler
	public ResponseEntity<ApiResponse> noSuchGoalException(NoSuchGoalException e) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.createErrorResponse(e.getMessage()));
	}
}
