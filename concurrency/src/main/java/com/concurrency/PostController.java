package com.concurrency;

import com.concurrency.model.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequiredArgsConstructor
@RequestMapping("/post")
@RestController
public class PostController {

  private final PostService postService;

  @PostMapping("/")
  public Post createPostDetail(@RequestBody Map<String, String> postData) {
    String title = postData.get("title");
    Post result = postService.createPost(title);

    return result;
  }

  @Transactional
  @GetMapping("/{id}")
  public Post getPostDetail(@PathVariable("id") long postId) {
    Post result = postService.getPost(postId);

    return result;
  }
}
