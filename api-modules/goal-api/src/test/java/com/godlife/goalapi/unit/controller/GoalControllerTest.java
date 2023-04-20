package com.godlife.goalapi.unit.controller;

import static com.godlife.goalapi.utils.restdoc.DocumentProvider.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.godlife.goalapi.api.GoalController;
import com.godlife.goalapi.utils.CreateGoalRequestFactory;
import com.godlife.goaldomain.dto.request.CreateGoalRequest;
import com.godlife.goaldomain.dto.request.UpdateGoalTodoScheduleRequest;
import com.godlife.goaldomain.service.GoalCommandService;
import com.godlife.goaldomain.service.GoalQueryService;

/*
    todo
    - 리스폰스, 리퀘스트 확정전까진 relaxed
 */

@AutoConfigureMockMvc
@AutoConfigureRestDocs
@WebMvcTest({GoalController.class})
class GoalControllerTest {
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	GoalCommandService goalCommandService;

	@MockBean
	GoalQueryService goalQueryService;

	private static final String USER_ID_HEADER = "x-user";
	private static final Long TEST_USER_ID = 1L;

	@Test
	@DisplayName("목표를 저장한다")
	void postGoals() throws Exception {
		//given & when
		ResultActions result = performPostSampleGoalsWithMindsetsAndTodos();

		//then
		result
			.andExpect(status().isCreated())
			.andDo(document("post-goals", getPostGoalsRequestFieldsSnippet(), getSuccessResponseFieldsSnippet()))
			.andDo(print());
	}

	@Test
	@DisplayName("모든 목표를 가져온다")
	void getAllGoals() throws Exception {
		//given
		performPostSampleGoalsWithMindsetsAndTodos();

		//when
		ResultActions result = performGetWithAuthorizationByUrlTemplate("/goals");

		//then
		result
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.data.[0]").exists())
			.andDo(document("get-goals",
				relaxedResponseFields(
					fieldWithPath("status").description("api 응답 상태"),
					fieldWithPath("message").description("api 응답 메시지"),
					fieldWithPath("data").description("api 응답 데이터"),
					fieldWithPath("data[].goalId").description("목표 아이디"),
					fieldWithPath("data[].title").description("목표 제목")
				)))
			.andDo(print());
	}

	@Test
	@DisplayName("미완료인 모든 목표를 가져온다")
	void getAllGoals1() throws Exception {
		//given
		performPostSampleGoalsWithMindsetsAndTodos();

		//when
		ResultActions result = mockMvc.perform(get("/goals")
			.header(USER_ID_HEADER, TEST_USER_ID)
			.queryParam("completionStatus", "false")
			.accept(MediaType.APPLICATION_JSON)
		);

		//then
		result
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.data.[0]").exists())
			.andDo(document("get-goals",
				relaxedResponseFields(
					fieldWithPath("status").description("api 응답 상태"),
					fieldWithPath("message").description("api 응답 메시지"),
					fieldWithPath("data").description("api 응답 데이터"),

					fieldWithPath("data[].goalId").optional().description("목표 아이디").type(Object.class),
					fieldWithPath("data[].title").optional().description("목표 제목").type(Object.class)
				)))
			.andDo(print());
	}

	@Test
	@DisplayName("모든 마인드셋을 가져온다")
	void getAllGoalsWithMindsets() throws Exception {
		//given
		for (int i = 0; i < 1; i++) {
			performPostSampleGoalsWithMindsetsAndTodos();
		}

		//when
		ResultActions result = mockMvc.perform(get("/goals/mindsets")
			.header(USER_ID_HEADER, TEST_USER_ID)
			.queryParam("page", "0")
			.queryParam("size", "5")
			.queryParam("completionStatus", "false")
			.accept(MediaType.APPLICATION_JSON));

		//then
		result
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.data.[0].mindsets").exists())
			.andDo(document("get-goals-with-mindsets",
				relaxedResponseFields(
					fieldWithPath("status").description("api 응답 상태"),
					fieldWithPath("message").description("api 응답 메시지"),
					fieldWithPath("data").description("api 응답 데이터"),

					fieldWithPath("data[].goalId").description("목표 아이디"),
					fieldWithPath("data[].title").description("목표 제목")
				)))
			.andDo(print());
	}

	@Test
	@DisplayName("MyList/캘린더 특정 년월의 일자별 투두카운트를 조회한다")
	void getDailyTodosCount() throws Exception {
		//given
		performPostSampleGoalsWithMindsetsAndTodos();

		//when
		mockMvc.perform(get("/goals/todos/counts")
				.header(USER_ID_HEADER, TEST_USER_ID)
				.queryParam("date", "202210")
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.data.[0].date").exists())
			.andDo(document("get-goals-with-todos-count", getSuccessResponseFieldsSnippet()))
			.andDo(print());

		//then
	}

	@Test
	@DisplayName("MyList/캘린더 특정 년월일의 투두리스트를 조회한다_미완료,완료 전체")
	void getDailyGoalsAndLowestDepthTodos1() throws Exception {
		//given
		performPostSampleGoalsWithMindsetsAndTodos();
		performPostSampleGoalsWithMindsetsAndTodos();

		//when
		mockMvc.perform(get("/goals/todos")
				.header(USER_ID_HEADER, TEST_USER_ID)
				.queryParam("date", "20221031")
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andDo(document("get-goals-with-todos", getSuccessResponseFieldsSnippet()))
			.andDo(print());
		//then
	}

	@Test
	@DisplayName("MyList/캘린더 특정 년월일의 투두리스트를 조회한다_미완료")
	void getDailyGoalsAndLowestDepthTodos2() throws Exception {
		//given
		performPostSampleGoalsWithMindsetsAndTodos();
		performPostSampleGoalsWithMindsetsAndTodos();

		//when
		mockMvc.perform(get("/goals/todos")
				.header(USER_ID_HEADER, TEST_USER_ID)
				.queryParam("date", "20221031")
				.queryParam("size", "1")
				.queryParam("completionStatus", "false")
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andDo(document("get-goals-with-todos", getSuccessResponseFieldsSnippet()))
			.andDo(print());
		//then
	}

	@Test
	@DisplayName("투두의 상세정보를 조회한다")
	void getTodoDetail() throws Exception {
		//given
		performPostSampleGoalsWithMindsetsAndTodos();

		//when
		mockMvc.perform(get("/goals/todos/{todoId}", 2)
				.header(USER_ID_HEADER, TEST_USER_ID)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			//                .andDo(document("get-goals-with-todos", getSuccessResponseFieldsSnippet()))
			.andDo(print());
		//then
	}

	@Test
	@DisplayName("목표의 상세정보를 조회한다")
	void getGoalDetail() throws Exception {
		//given
		performPostSampleGoalsWithMindsetsAndTodos();

		//when
		ResultActions result = mockMvc.perform(get("/goals/{goalId}", 1)
			.header(USER_ID_HEADER, TEST_USER_ID)
			.accept(MediaType.APPLICATION_JSON));
		;

		//then
		result
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.data.goalId").exists())
			.andDo(print());
	}

	@Test
	@DisplayName("투두상세 투두 일정을 조회한다.")
	void getTodoSchedules() throws Exception {
		//given
		performPostSampleGoalsWithMindsetsAndTodos();

		//when
		ResultActions result = mockMvc.perform(get("/goals/todos/{todoId}/todoSchedules", 2)
			.header(USER_ID_HEADER, TEST_USER_ID)
			.queryParam("page", "1")
			.queryParam("criteria", "before")
			.accept(MediaType.APPLICATION_JSON));

		//then
		result
			.andExpect(status().isOk())
			// .andExpect(jsonPath("$.data.goalId").exists())
			.andDo(print());
	}

	@Test
	@DisplayName("특정 년월일의 투두리스트에 완료체크를 한다")
	void put() throws Exception {
		//given
		performPostSampleGoalsWithMindsetsAndTodos();

		UpdateGoalTodoScheduleRequest updateGoalTodoScheduleRequest = UpdateGoalTodoScheduleRequest.builder()
			.completionStatus(true)
			.build();

		//when
		mockMvc.perform(patch("/goals/todoSchedules/{todoScheduleId}", 1)
				.header(USER_ID_HEADER, TEST_USER_ID)
				.content(objectMapper.writeValueAsString(updateGoalTodoScheduleRequest))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			//                .andDo(document("patch-goals-todoSchedule", getSuccessResponseFieldsSnippet()))
			.andDo(print());
		//then
	}

	private ResultActions performPostSampleGoalsWithMindsetsAndTodos() throws Exception {

		CreateGoalRequest createGoalRequest = CreateGoalRequestFactory.getCreateGoalRequest();

		return mockMvc.perform(
			post("/goals")
				.header(USER_ID_HEADER, TEST_USER_ID)
				.content(objectMapper.writeValueAsString(createGoalRequest))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)

		);
	}

	private ResultActions performGetWithAuthorizationByUrlTemplate(String urlTemplate) throws Exception {
		return mockMvc.perform(get(urlTemplate)
			.header(USER_ID_HEADER, TEST_USER_ID)
			.accept(MediaType.APPLICATION_JSON));
	}
}
