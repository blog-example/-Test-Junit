package com.concurrency;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController("/post")
public class PostController {

  private final PostRepository postRepository;

  @GetMapping("/{id}")
  public void getPostDetail(@PathVariable("id") long postId) {

  }
}
