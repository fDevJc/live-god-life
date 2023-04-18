package com.godlife.feedapi.acceptance;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestExecutionListeners;

import com.godlife.feedapi.common.RequestBuilder;
import com.godlife.feedapi.controller.dto.response.ApiResponse;
import com.godlife.feeddomain.dto.FeedsDto;

import io.restassured.RestAssured;
import io.restassured.mapper.TypeRef;
import io.restassured.response.Response;

@TestExecutionListeners(value = {AcceptanceTestExecutionListener.class,}, mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FeedAcceptanceTest {
	@LocalServerPort
	int port;

	@BeforeEach
	void init() {
		RestAssured.port = port;
	}

	@Test
	void 사용자는_피드를_등록할_수_있다() throws Exception {
		피드_등록_요청()
			.then().log().all()
			.statusCode(HttpStatus.CREATED.value());
	}

	private static Response 피드_등록_요청() {
		return RestAssured
			.given().log().all()
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.accept(MediaType.APPLICATION_JSON_VALUE)
			.header("x-user", 1L)
			.body(RequestBuilder.getCreateFeedRequest())
			.when()
			.post("/feeds");
	}

	@Test
	void 피드_조회_요청() throws Exception {
		//given
		피드_등록_요청();

		//when & then
		ApiResponse<List<FeedsDto>> ret = RestAssured
			.given().log().all()
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.accept(MediaType.APPLICATION_JSON_VALUE)
			.header("x-user", 1L)
			.when()
			.get("/feeds")
			.then().log().all()
			.statusCode(HttpStatus.OK.value())
			.extract()
			.as(new TypeRef<>() {
			});

		Assertions.assertThat(ret.getData().size()).isEqualTo(1);
	}

	@Test
	void 피드_상세_조회_요청() throws Exception {
		//given
		피드_등록_요청();

		//when & then
		RestAssured
			.given().log().all()
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.accept(MediaType.APPLICATION_JSON_VALUE)
			.header("x-user", 1L)
			.when()
			.get("/feeds/1")
			.then().log().all()
			.statusCode(HttpStatus.OK.value());
	}
}
