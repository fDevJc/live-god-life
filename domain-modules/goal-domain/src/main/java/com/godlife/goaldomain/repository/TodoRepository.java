package com.godlife.goaldomain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.godlife.goaldomain.domain.Goal;
import com.godlife.goaldomain.domain.Todo;

public interface TodoRepository extends JpaRepository<Todo, Long> {
	List<Todo> findAllByGoal(Goal goal);
}
