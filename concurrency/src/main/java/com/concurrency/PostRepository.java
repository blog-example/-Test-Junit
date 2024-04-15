package com.concurrency;

import com.concurrency.model.Post;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
  @Query("SELECT p FROM Post p where p.postId = :postId")
  @Lock(LockModeType.PESSIMISTIC_WRITE)
  Optional<Post> findPostWithLock (@Param("postId") long postId);
}
