package com.junit_example.concurrency.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data

@Entity
@Table(name = "posts")
public class Post {
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  @Column(name = "post_id")
  private long postId;

  @Column(name = "title")
  private String title;

  @Column(name = "view_count")
  private long viewCount;

  public void updateViewCount(long count) {
    this.viewCount = count;
  }

}
