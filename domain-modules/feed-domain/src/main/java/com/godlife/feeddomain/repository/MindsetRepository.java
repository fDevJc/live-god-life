package com.godlife.feeddomain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.godlife.feeddomain.domain.Mindset;

@Repository
public interface MindsetRepository extends JpaRepository<Mindset, Long> {
}
