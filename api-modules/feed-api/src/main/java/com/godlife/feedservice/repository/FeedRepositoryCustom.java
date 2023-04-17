package com.godlife.feedservice.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.godlife.feedservice.dto.FeedMindsetsTodosDto;
import com.godlife.feedservice.dto.FeedsDto;

public interface FeedRepositoryCustom {
	List<FeedsDto> findAllByCategoryAndFeedIds(Pageable page, String category, List<Long> feedIds);

	FeedMindsetsTodosDto findFeedWithMindsetsAndTodosByFeedId(Long feedId);
}
