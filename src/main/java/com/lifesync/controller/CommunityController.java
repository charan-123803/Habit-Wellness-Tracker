package com.lifesync.controller;

import com.lifesync.dto.ApiResponse;
import com.lifesync.entity.Post;
import com.lifesync.entity.User;
import com.lifesync.repository.PostRepository;
import com.lifesync.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/community")
@RequiredArgsConstructor
public class CommunityController {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @GetMapping("/posts")
    public ResponseEntity<ApiResponse<List<Post>>> getAllPosts() {
        return ResponseEntity.ok(ApiResponse.<List<Post>>builder()
                .success(true)
                .data(postRepository.findAllByOrderByCreatedAtDesc())
                .build());
    }

    @PostMapping("/posts/user/{userId}")
    public ResponseEntity<ApiResponse<Post>> createPost(@PathVariable Long userId, @RequestBody Post post) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        post.setUser(user);
        Post saved = postRepository.save(post);
        return ResponseEntity.ok(ApiResponse.<Post>builder()
                .success(true)
                .message("Post published")
                .data(saved)
                .build());
    }

    @PostMapping("/posts/{postId}/like")
    public ResponseEntity<ApiResponse<Post>> likePost(@PathVariable Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("Post not found"));
        post.setLikesCount(post.getLikesCount() + 1);
        return ResponseEntity.ok(ApiResponse.<Post>builder()
                .success(true)
                .data(postRepository.save(post))
                .build());
    }
}
