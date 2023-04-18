package com.godlife.feeddomain.repository.impl;

import static com.godlife.feeddomain.domain.QContent.*;
import static com.godlife.feeddomain.domain.QFeed.*;
import static com.godlife.feeddomain.domain.QMindset.*;
import static com.godlife.feeddomain.domain.QTodo.*;
import static com.godlife.feeddomain.domain.QTodoFolder.*;
import static com.godlife.feeddomain.domain.QTodoTask.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;

import org.springframework.data.domain.Pageable;

import com.godlife.feeddomain.enums.Category;
import com.godlife.feeddomain.dto.ContentDto;
import com.godlife.feeddomain.dto.FeedDto;
import com.godlife.feeddomain.dto.FeedMindsetsTodosDto;
import com.godlife.feeddomain.dto.MindsetDto;
import com.godlife.feeddomain.dto.QContentDto;
import com.godlife.feeddomain.dto.QFeedDto;
import com.godlife.feeddomain.dto.QFeedMindsetsTodosDto;
import com.godlife.feeddomain.dto.QMindsetDto;
import com.godlife.feeddomain.dto.QTodoDto;
import com.godlife.feeddomain.dto.TodoDto;
import com.godlife.feeddomain.exception.FeedNotFoundException;
import com.godlife.feeddomain.repository.FeedRepositoryCustom;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

public class FeedRepositoryCustomImpl implements FeedRepositoryCustom {
	private final JPAQueryFactory queryFactory;
	private static final int FIRST_DEPTH = 1;
	private static final int SECOND_DEPTH = 2;

	public FeedRepositoryCustomImpl(EntityManager em) {
		this.queryFactory = new JPAQueryFactory(em);
	}

	@Override
	public List<FeedDto> findAllByCategoryAndFeedIds(Pageable page, String category, List<Long> feedIds) {
		return queryFactory
			.select(
				new QFeedDto(
					feed.feedId,
					feed.title,
					feed.category.stringValue(),
					feed.viewCount,
					feed.pickCount,
					feed.todoCount,
					feed.todoScheduleDay,
					feed.thumbnailImage,
					feed.userId))
			.from(feed)
			.where(isFeedIdIn(feedIds),
				isEqCategory(category))
			.offset(page.getOffset())
			.limit(page.getPageSize())
			.orderBy(feed.updatedDate.desc())
			.fetch();
	}

	@Override
	public FeedMindsetsTodosDto findFeedWithMindsetsAndTodosByFeedId(Long feedId) {
		// updateFeedViewCount(feedId);

		FeedMindsetsTodosDto feedMindsetsTodosDto = findFeedDtoByFeedId(feedId).orElseThrow(() -> new FeedNotFoundException(feedId));

		feedMindsetsTodosDto.registerContentDtos(findContentDtosByFeedId(feedId));

		feedMindsetsTodosDto.registerMindsetDtos(findMindsetDtosByFeedId(feedId));

		List<TodoDto> parentTodoDtos = findParentTodoDtosByFeedId(feedId);
		List<TodoDto> childTodoDtos = findChildTodoDtosByFeedId(feedId);
		setChildTodoDtosInParentTodoDtos(parentTodoDtos, childTodoDtos);
		feedMindsetsTodosDto.registerTodoDtos(parentTodoDtos);

		return feedMindsetsTodosDto;
	}

	private List<ContentDto> findContentDtosByFeedId(Long feedId) {
		return queryFactory
			.select(new QContentDto(
				content1.title,
				content1.content,
				content1.orderNumber
			))
			.from(content1)
			.where(content1.feed.feedId.eq(feedId))
			.fetch();
	}

	private static void setChildTodoDtosInParentTodoDtos(List<TodoDto> todoDtos, List<TodoDto> childTodoDtos) {
		todoDtos.forEach(todoDto -> todoDto.registerChildTodos(
			childTodoDtos.stream()
				.filter(childTodos -> childTodos.getParentTodoId().equals(todoDto.getTodoId()))
				.collect(Collectors.toList())
		));
	}

	private List<TodoDto> findChildTodoDtosByFeedId(Long feedId) {
		return queryFactory
			.select(
				new QTodoDto(
					todo.todoId,
					todoTask.todo.todoId,
					todo.type,
					todo.title,
					todo.depth,
					todo.orderNumber,
					todo.period,
					todoTask.startDay,
					todoTask.endDay,
					todoTask.repetitionType.stringValue(),
					todoTask.repetitionParams,
					todoTask.notification))
			.from(todo)
			.leftJoin(todoTask).on(todoTask.eq(todo))
			.leftJoin(todoFolder).on(todoFolder.eq(todo))
			.where(todo.feed.feedId.eq(feedId),
				todo.depth.eq(SECOND_DEPTH))
			.fetch();
	}

	private List<TodoDto> findParentTodoDtosByFeedId(Long feedId) {
		return queryFactory
			.select(
				new QTodoDto(
					todo.todoId,
					todo.type,
					todo.title,
					todo.depth,
					todo.orderNumber,
					todo.period,
					todoTask.startDay,
					todoTask.endDay,
					todoTask.repetitionType.stringValue(),
					todoTask.repetitionParams,
					todoTask.notification
				)
			)
			.from(todo)
			.leftJoin(todoTask).on(todoTask.eq(todo))
			.leftJoin(todoFolder).on(todoFolder.eq(todo))
			.where(todo.feed.feedId.eq(feedId),
				todo.depth.eq(FIRST_DEPTH))
			.fetch();
	}

	private List<MindsetDto> findMindsetDtosByFeedId(Long feedId) {
		return queryFactory
			.select(
				new QMindsetDto(
					mindset.mindsetId,
					mindset.content))
			.from(mindset)
			.where(mindset.feed.feedId.eq(feedId))
			.fetch();
	}

	private Optional<FeedMindsetsTodosDto> findFeedDtoByFeedId(Long feedId) {
		return Optional.ofNullable(queryFactory
			.select(
				new QFeedMindsetsTodosDto(
					feed.feedId,
					feed.category,
					feed.title,
					feed.userId,
					feed.viewCount,
					feed.pickCount,
					feed.todoCount,
					feed.todoScheduleDay,
					feed.image
				))
			.from(feed)
			.where(feed.feedId.eq(feedId))
			.fetchOne());
	}

	private void updateFeedViewCount(Long feedId) {
		queryFactory
			.update(feed)
			.set(feed.viewCount, feed.viewCount.add(1))
			.where(feed.feedId.eq(feedId))
			.execute();
	}

	private static BooleanExpression isEqCategory(String category) {
		if (Objects.isNull(category) || Category.ALL.toString().equals(category)) {
			return null;
		}
		return feed.category.eq(Category.valueOf(category));
	}

	private BooleanExpression isFeedIdIn(List<Long> feedIds) {
		if (Objects.isNull(feedIds)) {
			return null;
		}
		return feed.feedId.in(feedIds);
	}
}
