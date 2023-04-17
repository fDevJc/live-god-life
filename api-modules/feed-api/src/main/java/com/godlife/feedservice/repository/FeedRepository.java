package com.godlife.feedservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.godlife.feedservice.domain.Feed;

public interface FeedRepository extends JpaRepository<Feed, Long>, FeedRepositoryCustom {
}
