package com.godlife.feeddomain.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.godlife.feeddomain.domain.Content;
import com.godlife.feeddomain.domain.Mindset;
import com.godlife.feeddomain.domain.Todo;
import com.godlife.feeddomain.repository.ContentRepository;
import com.godlife.feeddomain.repository.MindsetRepository;
import com.godlife.feeddomain.repository.TodoRepository;

import lombok.RequiredArgsConstructor;

@Transactional
@RequiredArgsConstructor
@Service
public class FeedCreateService {
	private final ContentRepository contentRepository;
	private final MindsetRepository mindsetRepository;
	private final TodoRepository todoRepository;

	public void createFeed(List<Content> contents, List<Mindset> mindsets, List<Todo> todos) {
		contentRepository.saveAll(contents);
		mindsetRepository.saveAll(mindsets);
		todoRepository.saveAll(todos);
	}
}
