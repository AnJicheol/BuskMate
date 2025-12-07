package org.example.buskmate.community.service;

import org.example.buskmate.community.dto.CommunityCommentCreateRequestDto;
import org.example.buskmate.community.dto.CommunityCommentResponseDto;
import org.example.buskmate.community.dto.CommunityCommentUpdateRequestDto;

import java.util.List;

public interface CommunityCommentService {

    // 게시글별 댓글 목록 조회
    List<CommunityCommentResponseDto> getCommentsByPostId(Long postId);

    // 댓글 생성
    CommunityCommentResponseDto createComment(Long postId, String authorId, CommunityCommentCreateRequestDto requestDto);

    // 댓글 수정
    CommunityCommentResponseDto updateComment(Long commentId, CommunityCommentUpdateRequestDto requestDto);

    // 댓글 삭제
    void deleteComment(Long commentId);

    // post당 댓글 수 조회
    Long countByCommunityPostId(Long postId);

}
