package com.godlife.feedapi.application;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.godlife.feeddomain.dto.FeedsDto;

@Service
public class FeedQueryService {

	public List<FeedsDto> getFeeds(Pageable page, Long userId, String category, List<Long> feedIds) {
		return null;
	}
}
