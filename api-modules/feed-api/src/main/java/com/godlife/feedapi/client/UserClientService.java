package com.godlife.feedapi.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.godlife.feedapi.client.response.BookmarkResponse;
import com.godlife.feedapi.client.response.UserResponse;

@FeignClient(name = "user-service")
public interface UserClientService {
	@GetMapping("/users")
	UserResponse getUsers(@RequestParam String ids);

	@GetMapping("/users/{userId}/bookmarks")
	BookmarkResponse getBookmarks(@PathVariable Long userId, @RequestParam String feedIds);
}
