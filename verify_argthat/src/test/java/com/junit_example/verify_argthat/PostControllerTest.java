package com.junit_example.verify_argthat;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(PostController.class)
public class PostControllerTest {
  private final String baseUrl = "http://localhost:8080";

  private final long dummyId = 1;

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockBean
  private PostService postService;

  @Test
  public void 조회하려는_게시글의_id를_서비스에_전달한다() throws Exception {
    long postId = 1;
    String url = baseUrl + "/post" + "/" + postId;

    mockMvc.perform(get(url))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.isSuccess").isNotEmpty());

    verify(postService).getPost(eq(postId));
  }

  @Test
  public void 게시글의_제목을_서비스에_전달한다() throws Exception {
    String url = baseUrl + "/post/";

    Map<String, String> dummyInput = Map.of("title", "It is a dummy title");
    String body = objectMapper.writeValueAsString(dummyInput);

    mockMvc.perform(post(url)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(body))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.isSuccess", Matchers.equalTo(true)));

    verify(postService).createPost(argThat(
            newPostDto -> newPostDto.getTitle().equals(dummyInput.get("title"))
    ));
  }

  @Test
  public void 페이징_처리에_사용될_데이터를_전달한다() throws Exception {
    String url = baseUrl + "/post/";
    String page = "1";
    String size = "10";
    String sort = "id";

    mockMvc.perform(get(url)
                    .param("page", page)
                    .param("size", size)
                    .param("sort", sort))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.isSuccess").doesNotExist());

    verify(postService).getPostList((argThat(pageable -> {
      return pageable.getPageNumber() == Integer.parseInt(page)
              && pageable.getPageSize() == Integer.parseInt(size)
              && pageable.getSort().getOrderFor(sort) != null
              && pageable.getSort().getOrderFor(sort).getDirection() == Sort.Direction.ASC;
    })));
  }

}
