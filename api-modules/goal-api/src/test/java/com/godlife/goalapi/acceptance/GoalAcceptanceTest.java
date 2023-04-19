package com.godlife.goalapi.acceptance;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestExecutionListeners;

import com.godlife.goalapi.utils.CreateGoalRequestFactory;
import com.godlife.goaldomain.dto.request.CreateGoalRequest;

import io.restassured.RestAssured;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestExecutionListeners(
	value = {AcceptanceTestExecutionListener.class},
	mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS
)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GoalAcceptanceTest {
	@LocalServerPort
	int port;

	@BeforeEach
	void init() {
		RestAssured.port = port;
	}

	@Test
	void 사용자는_목표를_저장할_수_있다() throws Exception {
		CreateGoalRequest createGoalRequest = CreateGoalRequestFactory.getCreateGoalRequest();

		RestAssured
			.given().log().all()
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.accept(MediaType.APPLICATION_JSON_VALUE)
			.header("x-user", 1L)
			.body(createGoalRequest)
			.when()
			.post("/goals")
			.then().log().all()
			.statusCode(HttpStatus.CREATED.value());
	}
}
