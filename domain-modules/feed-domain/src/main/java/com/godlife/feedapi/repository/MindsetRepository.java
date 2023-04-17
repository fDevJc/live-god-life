package com.godlife.feedapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.godlife.feedapi.domain.Mindset;

@Repository
public interface MindsetRepository extends JpaRepository<Mindset, Long> {
}
