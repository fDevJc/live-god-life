package com.godlife.goaldomain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.godlife.goaldomain.domain.Goal;
import com.godlife.goaldomain.domain.Mindset;

public interface MindsetRepository extends JpaRepository<Mindset, Long> {
	List<Mindset> findAllByGoal(Goal goal);
}
