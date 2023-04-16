package com.godlife.feedservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.godlife.feedservice.domain.Mindset;

@Repository
public interface MindsetRepository extends JpaRepository<Mindset, Long> {
}
