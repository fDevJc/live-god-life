package com.godlife.feeddomain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.godlife.feeddomain.domain.Content;

public interface ContentRepository extends JpaRepository<Content, Long> {
}
