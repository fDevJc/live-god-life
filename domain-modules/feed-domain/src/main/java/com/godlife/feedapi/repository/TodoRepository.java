package com.godlife.feedapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.godlife.feedapi.domain.Todo;

public interface TodoRepository extends JpaRepository<Todo, Long> {
}
