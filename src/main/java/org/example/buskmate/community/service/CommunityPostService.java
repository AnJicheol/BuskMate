package org.example.buskmate.community.service;

import org.example.buskmate.community.dto.post.crud.request.*;
import org.example.buskmate.community.dto.post.crud.response.CommunityPostReadAllPostResponse;
import org.example.buskmate.community.dto.post.crud.response.CommunityPostReadPostResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommunityPostService {

    // 1. 게시글 생성
    void createPost(CommunityPostCreatePostRequest request, String authorId);
    // 2. 전체 게시글 조회
    Page<CommunityPostReadAllPostResponse> getAllPost(Pageable pageable, String authorId, CommunityPostReadAllPostRequest request);
    // 3. 특정 게시글 조회
    CommunityPostReadPostResponse getPostId(String viewerId, Long postId, CommunityPostReadPostRequest request);
    // 4. 게시글 수정
    void updatePost(String authorId, Long postId, CommunityPostUpdatePostRequest request);
    // 5. 게시글 삭제
    void deletePost(String authorId, Long id);
}
