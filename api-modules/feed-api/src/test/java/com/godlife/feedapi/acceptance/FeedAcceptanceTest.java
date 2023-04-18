package com.godlife.feedapi.acceptance;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;

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
	    // //given
		// RestAssured.given().log().all()
		// 	.contentType(MediaType.APPLICATION_JSON_VALUE)
		// 	.accept(MediaType.APPLICATION_JSON_VALUE)
		// 	.header("x-user", 1L)
		// 	.body(ADD_ROOM_REQUEST)
		// 	.when()
		// 	.post("/api/v1/rooms");
	    // //when
		//
	    // //then
	}
}
