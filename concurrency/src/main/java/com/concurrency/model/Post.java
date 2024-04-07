package com.concurrency.model;

import jakarta.persistence.*;

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

}
