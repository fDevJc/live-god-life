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

import io.restassured.RestAssured;
import io.restassured.mapper.TypeRef;
import io.restassured.response.Response;

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

	private final Long TEST_USER_ID = 1L;

	@Test
	void 사용자는_목표를_저장할_수_있다() throws Exception {
		목표_저장_요청()
			.then().log().all()
			.statusCode(HttpStatus.CREATED.value());
	}

	private Response 목표_저장_요청() {
		return RestAssured
			.given().log().all()
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.accept(MediaType.APPLICATION_JSON_VALUE)
			.header("x-user", TEST_USER_ID)
			.body(CreateGoalRequestFactory.getCreateGoalRequest())
			.when()
			.post("/goals");
	}

	@Test
	void 사용자는_자신의_모든_목표를_조회할_수_있다() throws Exception {
		목표_저장_요청();
		//ApiResponse<List<GoalDto>> ret =
		RestAssured
			.given()
			.accept(MediaType.APPLICATION_JSON_VALUE)
			.header("x-user", TEST_USER_ID)
			.when()
			.get("/goals")
			.then().log().all()
			.statusCode(HttpStatus.OK.value())
			.extract()
			.as(new TypeRef<>() {
			});

		// Assertions.assertThat(ret.getData().size()).isEqualTo(1);
	}
}
