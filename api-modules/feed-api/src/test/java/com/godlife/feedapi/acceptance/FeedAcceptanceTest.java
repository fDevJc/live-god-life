package com.godlife.feedapi.acceptance;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import com.godlife.feedapi.presentation.dto.request.CreateFeedRequest;
import com.godlife.feeddomain.domain.enums.Category;
import com.godlife.feeddomain.domain.enums.RepetitionType;
import com.godlife.feeddomain.domain.enums.TodoType;

import io.restassured.RestAssured;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FeedAcceptanceTest {
	@LocalServerPort
	int port;

	@BeforeEach
	void init() {
		RestAssured.port = port;
	}

	@Test
	void 피드_등록_요청() throws Exception {
		//given
		CreateFeedRequest.CreateFeedContentRequest createFeedContentRequest = CreateFeedRequest.CreateFeedContentRequest.builder()
			.title("content-title")
			.content("content-content")
			.orderNumber(0)
			.build();

		CreateFeedRequest.CreateFeedMindsetRequest createFeedMindsetRequest = CreateFeedRequest.CreateFeedMindsetRequest.builder()
			.content("mindset-content")
			.build();

		CreateFeedRequest.CreateFeedTodoRequest createFeedTodoRequest = CreateFeedRequest.CreateFeedTodoRequest.builder()
			.title("toto-task")
			.depth(1)
			.notification("0900")
			.orderNumber(0)
			.type(TodoType.TASK.name())
			.period(90)
			.repetitionType(RepetitionType.DAY.name())
			.build();

		CreateFeedRequest createFeedRequest = CreateFeedRequest.builder()
			.categoryCode(Category.CAREER.name())
			.image("image")
			.title("feed-title")
			.todos(List.of(createFeedTodoRequest))
			.contents(List.of(createFeedContentRequest))
			.mindsets(List.of(createFeedMindsetRequest))
			.userId(1L)
			.build();

		RestAssured
			.given().log().all()
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.accept(MediaType.APPLICATION_JSON_VALUE)
			.header("x-user", 1L)
			.body(createFeedRequest)
			.when()
			.post("/feeds")
			.then().log().all()
			.statusCode(HttpStatus.CREATED.value());
	}

	@Test
	void 피드_조회_요청() throws Exception {
		CreateFeedRequest.CreateFeedContentRequest createFeedContentRequest = CreateFeedRequest.CreateFeedContentRequest.builder()
			.title("content-title")
			.content("content-content")
			.orderNumber(0)
			.build();

		CreateFeedRequest.CreateFeedMindsetRequest createFeedMindsetRequest = CreateFeedRequest.CreateFeedMindsetRequest.builder()
			.content("mindset-content")
			.build();

		CreateFeedRequest.CreateFeedTodoRequest createFeedTodoRequest = CreateFeedRequest.CreateFeedTodoRequest.builder()
			.title("toto-task")
			.depth(1)
			.notification("0900")
			.orderNumber(0)
			.type(TodoType.TASK.name())
			.period(90)
			.repetitionType(RepetitionType.DAY.name())
			.build();

		CreateFeedRequest createFeedRequest = CreateFeedRequest.builder()
			.categoryCode(Category.CAREER.name())
			.image("image")
			.title("feed-title")
			.todos(List.of(createFeedTodoRequest))
			.contents(List.of(createFeedContentRequest))
			.mindsets(List.of(createFeedMindsetRequest))
			.userId(1L)
			.build();

		RestAssured
			.given().log().all()
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.accept(MediaType.APPLICATION_JSON_VALUE)
			.header("x-user", 1L)
			.body(createFeedRequest)
			.when()
			.post("/feeds")
			.then().log().all()
			.statusCode(HttpStatus.CREATED.value());

		RestAssured
			.given().log().all()
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.accept(MediaType.APPLICATION_JSON_VALUE)
			.header("x-user", 1L)
			.when()
			.get("/feeds")
			.then().log().all()
			.statusCode(HttpStatus.OK.value());
	}

	@Test
	void 피드_상세_조회_요청() throws Exception {
	    //given

	    //when

	    //then
	}
}
