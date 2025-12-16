package org.example.buskmate.community.service;

import org.example.buskmate.community.dto.CommunityCommentCreateRequestDto;
import org.example.buskmate.community.dto.CommunityCommentResponseDto;
import org.example.buskmate.community.dto.CommunityCommentUpdateRequestDto;

import java.util.List;

/**
 * 댓글 서비스 인터페이스
 * - 댓글 조회/생성/수정/삭제 및 게시글 삭제 시 일괄 소프트 삭제 기능을 정의한다.
 */
public interface CommunityCommentService {

    /**
     * 게시글별 댓글 목록을 조회한다.
     */
    List<CommunityCommentResponseDto> getCommentsByPostId(Long postId);

    /**
     * 특정 게시글에 댓글을 생성한다.
     */
    CommunityCommentResponseDto createComment(Long postId, String authorId, CommunityCommentCreateRequestDto requestDto);

    /**
     * 댓글을 수정한다.
     */
    CommunityCommentResponseDto updateComment(Long commentId, CommunityCommentUpdateRequestDto requestDto);

    /**
     * 댓글을 소프트 삭제한다.
     */
    void deleteComment(Long commentId);

    /**
     * 게시물 삭제 시 해당 게시물의 활성 댓글들을 일괄 소프트 삭제한다.
     */
    void softDeleteCommentsByPostId(Long postId);
}
