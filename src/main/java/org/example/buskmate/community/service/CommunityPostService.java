package org.example.buskmate.community.service;

import org.example.buskmate.community.dto.post.crud.request.*;
import org.example.buskmate.community.dto.post.crud.response.CommunityPostReadAllPostResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * 게시글 서비스 인터페이스
 * - 게시글 생성/목록 조회/수정/삭제 기능을 정의한다.
 */
public interface CommunityPostService {

    /**
     * 게시글을 생성한다.
     */
    void createPost(CommunityPostCreatePostRequest request, String authorId);

    /**
     * 전체 게시글 목록을 페이징으로 조회한다.
     */
    Page<CommunityPostReadAllPostResponse> getAllPost(Pageable pageable, String authorId);

    /**
     * 게시글을 수정한다.
     */
    void updatePost(String authorId, Long postId, CommunityPostUpdatePostRequest request);

    /**
     * 게시글을 소프트 삭제한다.
     */
    void deletePost(String authorId, Long id);
}
