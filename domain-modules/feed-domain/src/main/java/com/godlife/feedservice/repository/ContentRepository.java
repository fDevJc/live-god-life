package com.godlife.feedservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.godlife.feedservice.domain.Content;

public interface ContentRepository extends JpaRepository<Content, Long> {
}
