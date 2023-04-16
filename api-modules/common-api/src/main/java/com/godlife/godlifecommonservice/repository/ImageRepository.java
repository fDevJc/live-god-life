package com.godlife.godlifecommonservice.repository;

import com.godlife.godlifecommonservice.domain.entity.image.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {

}
