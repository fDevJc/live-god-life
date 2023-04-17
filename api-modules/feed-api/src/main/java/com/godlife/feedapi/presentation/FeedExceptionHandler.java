package com.godlife.feedapi.presentation;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.godlife.feedapi.presentation.dto.response.ApiResponse;
import com.godlife.feedapi.presentation.dto.response.ResponseCode;
import com.godlife.feeddomain.exception.NoSuchFeedException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class FeedExceptionHandler {
	@ExceptionHandler
	public ResponseEntity<ApiResponse> noSuchFeedException(NoSuchFeedException e) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(ResponseCode.ERROR, e.getMessage()));
	}
}