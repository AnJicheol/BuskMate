package org.example.buskmate.community.service;

import org.example.buskmate.community.dto.post.crud.request.*;
import org.example.buskmate.community.dto.post.crud.response.CommunityPostReadAllPostResponse;
import org.example.buskmate.community.dto.post.crud.response.CommunityPostReadPostResponse;
import org.springframework.data.domain.Page;

public interface CommunityPostService {

    void createPost(CommunityPostCreatePostRequest request);

    Page<CommunityPostReadAllPostResponse> getAllPost(CommunityPostReadAllPostRequest request);

    CommunityPostReadPostResponse getPostId(Long id, CommunityPostReadPostRequest request);

    void updatePost(Long id, CommunityPostUpdatePostRequest request);

    void deletePost(Long id, CommunityPostDeletePostRequest request);
}
