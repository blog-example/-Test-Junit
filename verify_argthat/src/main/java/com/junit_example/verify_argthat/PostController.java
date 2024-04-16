package com.junit_example.verify_argthat;


import com.junit_example.verify_argthat.dto.NewPostDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;


import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/post")
@RestController
public class PostController {

  private final PostService postService;

  @GetMapping("/{id}")
  public Map<String, Boolean> getPostDetail(@PathVariable("id") long postId) {
    log.info(postId + "");
    postService.getPost(postId);

    return Map.of("isSuccess", false);
  }

  @PostMapping("/")
  public Map<String, Boolean> createPostDetail(@RequestBody NewPostDto postData) {
    postService.createPost(postData);

    return Map.of("isSuccess", true);
  }

  @GetMapping("/")
  public Map<String, Boolean> getPostList(Pageable pageable) {
    log.info(pageable.getSort() + "");
    postService.getPostList(pageable);

    return null;
  }
}
