package com.godlife.feedservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.godlife.feedservice.domain.Todo;

public interface TodoRepository extends JpaRepository<Todo, Long> {
}
