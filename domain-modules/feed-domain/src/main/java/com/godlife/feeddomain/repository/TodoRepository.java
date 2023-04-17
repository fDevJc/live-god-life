package com.godlife.feeddomain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.godlife.feeddomain.domain.Todo;

public interface TodoRepository extends JpaRepository<Todo, Long> {
}
