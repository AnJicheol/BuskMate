package org.example.buskmate.community.service;

import org.example.buskmate.community.dto.crud.request.*;
import org.example.buskmate.community.dto.crud.response.ReadAllPostResponse;
import org.example.buskmate.community.dto.crud.response.ReadPostResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CommunityPostService {

    void createPost(CreatePostRequest request);

    Page<ReadAllPostResponse> getAllPost(ReadAllPostRequest request);

    ReadPostResponse getPostId(Integer id, ReadPostRequest request);

    void updatePost(Integer id, UpdatePostRequest request);

    void deletePost(Integer id, DeletePostRequest request);
}
