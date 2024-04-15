package com.concurrency;


import com.concurrency.model.Post;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class PostServiceTest {

  @Autowired
  private PostRepository postRepository;

  @Autowired
  private PostService postService;


  Post post;

  @BeforeEach
  public void setup() {
    post = Post.builder().title("Dummy post").build();
    postRepository.save(post);
  }

  @AfterEach
  public void cleanup() {
    post = null;
    postRepository.deleteAll();
  }

  @Test
  public void 한_게시글을_1회_조회하면_조회수가_1증가한다() {
    long postId = post.getPostId();

    Post beforeQuery = postRepository.findById(postId).get();
    postService.getPost(postId);
    Post afterQuery = postRepository.findById(postId).get();

    assertEquals(beforeQuery.getViewCount() + 1, afterQuery.getViewCount());
  }

  @Test
  public void 한_게시글을_10회_조회_할_경우_조회수가_10이_증가한다() {
    long postId = post.getPostId();

    Post beforeQuery = postRepository.findById(postId).get();

    for (int i = 0; i < 10; i++) {
      postService.getPost(postId);
    }

    Post afterQuery = postRepository.findById(postId).get();

    assertEquals(beforeQuery.getViewCount() + 10, afterQuery.getViewCount());
  }

  @Test
  public void 한_게시글을_동시에_10명의_유저가_조회를_할_경우_조회수가_10이_증가한다() {
    long postId = post.getPostId();
    int concurrencyCount = 1000;

    ExecutorService executorService = Executors.newFixedThreadPool(concurrencyCount);
    CountDownLatch latch = new CountDownLatch(concurrencyCount);

    Post beforeQuery = postRepository.findById(postId).get();

    try {
      for (int i = 0; i < concurrencyCount; i++) {
        executorService.submit(() -> {
          try {
            postService.getPost(postId);
          } finally {
            latch.countDown();
          }
        });
      }

      latch.await();
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    } finally {
      executorService.shutdown();
    }

    Post afterQuery = postRepository.findById(postId).get();
    assertEquals(beforeQuery.getViewCount() + concurrencyCount, afterQuery.getViewCount());
  }

}
