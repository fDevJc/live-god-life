package com.godlife.feedapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.godlife.feedapi.domain.Content;

public interface ContentRepository extends JpaRepository<Content, Long> {
}
