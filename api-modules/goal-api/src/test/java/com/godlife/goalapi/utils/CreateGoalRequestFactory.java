package com.godlife.goalapi.utils;

import static com.godlife.goalapi.utils.SampleTestDataCreator.*;

import java.util.List;

import com.godlife.goaldomain.dto.request.CreateGoalMindsetRequest;
import com.godlife.goaldomain.dto.request.CreateGoalRequest;
import com.godlife.goaldomain.dto.request.CreateGoalTodoRequest;

public class CreateGoalRequestFactory {
	public static CreateGoalRequest getCreateGoalRequest() {
		CreateGoalMindsetRequest createGoalMindsetRequest1 = new CreateGoalMindsetRequest(
			"사는건 레벨업이 아닌 스펙트럼을 넓히는 거란 얘길 들었다. 어떤 말보다 용기가 된다111.");

		CreateGoalMindsetRequest createGoalMindsetRequest2 = new CreateGoalMindsetRequest(
			"사는건 레벨업이 아닌 스펙트럼을 넓히는 거란 얘길 들었다. 어떤 말보다 용기가 된다222.");

		CreateGoalMindsetRequest createGoalMindsetRequest3 = new CreateGoalMindsetRequest(
			"사는건 레벨업이 아닌 스펙트럼을 넓히는 거란 얘길 들었다. 어떤 말보다 용기가 된다333.");

		CreateGoalTodoRequest todoFolder1 = getCreateGoalTodoFolderRequest(
			"포폴완성",
			1,
			1,
			List.of(
				getCreateGoalTodoTaskRequest(
					"컨셉잡기",
					2,
					0,
					"20221001",
					"20221031",
					"DAY",
					null,
					"0900"
				),
				getCreateGoalTodoTaskRequest(
					"스케치",
					2,
					1,
					"20221101",
					"20221131",
					"WEEK",
					List.of("월", "수", "금", "토"),
					"0900"
				),
				getCreateGoalTodoTaskRequest(
					"UI 작업",
					2,
					2,
					"20221001",
					"20221231",
					"NONE",
					null,
					"0900"
				)
			)
		);

		CreateGoalTodoRequest todoFolder2 = getCreateGoalTodoFolderRequest(
			"개발프로젝트 해보기",
			1,
			2,
			List.of(
				getCreateGoalTodoTaskRequest(
					"IT 동아리 서류 내기",
					2,
					0,
					"20221001",
					"20221031",
					"NONE",
					null,
					"0900"
				),
				getCreateGoalTodoTaskRequest(
					"파이썬 공부",
					2,
					0,
					"20221001",
					"20221031",
					"WEEK",
					List.of("월", "수", "금"),
					"0900"
				)
			)
		);

		CreateGoalTodoRequest todoTask1 = getCreateGoalTodoTaskRequest(
			"외주",
			1,
			3,
			"20221001",
			"20221031",
			"NONE",
			null,
			"0900"
		);

		return CreateGoalRequest.builder()
			.title("이직하기")
			.categoryName("커리어")
			.categoryCode("CAREER")
			.mindsets(List.of(createGoalMindsetRequest1, createGoalMindsetRequest2, createGoalMindsetRequest3))
			.todos(List.of(todoFolder1, todoFolder2, todoTask1))
			.build();
	}
}
