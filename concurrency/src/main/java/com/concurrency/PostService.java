package com.concurrency;

import com.concurrency.model.Post;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class PostService {
  private final PostRepository postRepository;

  public Post createPost(String title) {
    Post post = Post.builder()
            .title(title)
            .build();

    postRepository.save(post);

    return post;
  }

  @Transactional
  public Post getPost(long postId) {
    Post post = postRepository.findPostWithLock(postId)
            .orElseThrow(() -> new EntityNotFoundException());

    post.updateViewCount(post.getViewCount() + 1);

    return post;
  }
}
