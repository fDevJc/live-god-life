package com.godlife.godlifecommonservice.repository;

import com.godlife.godlifecommonservice.domain.entity.Term;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TermRepository extends JpaRepository<Term, Long> {

    /**
     * 타입에 맞는 약관 버전 최신 1개 조회
     */
    Term findTop1ByTypeOrderByVersionDesc(String type);
}
