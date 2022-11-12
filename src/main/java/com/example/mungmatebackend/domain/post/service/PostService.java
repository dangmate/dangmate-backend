package com.example.mungmatebackend.domain.post.service;

import com.example.mungmatebackend.api.posts.dto.PostsDto;
import com.example.mungmatebackend.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {

  private final PostRepository postRepository;

  public PostsDto.PostsResponse getPosts(PostsDto.PostsRequest request) {



    return PostsDto.PostsResponse.builder().build();
  }

}
