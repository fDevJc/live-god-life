package com.godlife.feedapi.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.godlife.feedapi.dto.FeedMindsetsTodosDto;
import com.godlife.feedapi.dto.FeedsDto;

public interface FeedRepositoryCustom {
	List<FeedsDto> findAllByCategoryAndFeedIds(Pageable page, String category, List<Long> feedIds);

	FeedMindsetsTodosDto findFeedWithMindsetsAndTodosByFeedId(Long feedId);
}
