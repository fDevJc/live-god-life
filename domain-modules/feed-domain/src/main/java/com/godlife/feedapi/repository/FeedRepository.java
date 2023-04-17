package com.godlife.feedapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.godlife.feedapi.domain.Feed;

public interface FeedRepository extends JpaRepository<Feed, Long>, FeedRepositoryCustom {
}
