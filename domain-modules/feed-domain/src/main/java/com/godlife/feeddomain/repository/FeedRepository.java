package com.godlife.feeddomain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.godlife.feeddomain.domain.Feed;

public interface FeedRepository extends JpaRepository<Feed, Long>, FeedRepositoryCustom {
}
