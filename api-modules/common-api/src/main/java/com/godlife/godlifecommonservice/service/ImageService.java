package com.godlife.godlifecommonservice.service;

import com.godlife.godlifecommonservice.domain.dto.ImageDto;
import com.godlife.godlifecommonservice.domain.entity.image.Image;
import com.godlife.godlifecommonservice.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ImageService {

    /**
     * 이미지 Repository
     */
    private final ImageRepository imageRepository;

    /**
     * 전체 프로필 이미지 조회
     *
     * @return
     */
    public List<ImageDto> getProfileImages() {
        return imageRepository.findAll()
                .stream()
                .map(image -> ImageDto.of(image.getUrl()))
                .collect(Collectors.toList());
    }

    /**
     * 프로필 이미지 추가
     */
    public void insertProfileImage(String url) {
        Image image = Image.builder()
                .url(url)
                .build();

        imageRepository.save(image);
    }
}
