package com.godlife.godlifecommonservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.godlife.godlifecommonservice.domain.entity.Code;

@Repository
public interface CodeRepository extends JpaRepository<Code, Long> {
	List<Code> findAllByKind(String kind);
}
