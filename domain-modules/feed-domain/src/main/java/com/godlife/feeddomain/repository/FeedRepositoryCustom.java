package com.godlife.feeddomain.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.godlife.feeddomain.dto.FeedMindsetsTodosDto;
import com.godlife.feeddomain.dto.FeedsDto;

public interface FeedRepositoryCustom {
	List<FeedsDto> findAllByCategoryAndFeedIds(Pageable page, String category, List<Long> feedIds);

	FeedMindsetsTodosDto findFeedWithMindsetsAndTodosByFeedId(Long feedId);
}
