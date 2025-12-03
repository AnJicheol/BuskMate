package org.example.buskmate.community.controller;

import lombok.RequiredArgsConstructor;
import org.example.buskmate.community.dto.CommunityCommentCreateRequestDto;
import org.example.buskmate.community.dto.CommunityCommentResponseDto;
import org.example.buskmate.community.dto.CommunityCommentUpdateRequestDto;
import org.example.buskmate.community.service.CommunityCommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/community")
public class CommunityCommentController {

    private final CommunityCommentService commentService;

    // 특정 게시글의 댓글 목록 조회
    @GetMapping("/posts/{postId}/comments")
    public ResponseEntity<List<CommunityCommentResponseDto>> getComments(
            @PathVariable Long postId
    ) {
        return ResponseEntity.ok(commentService.getCommentsByPostId(postId));
    }

    // 댓글 작성
    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommunityCommentResponseDto> createComment(
            @PathVariable Long postId,
            @AuthenticationPrincipal String authorId,
            @RequestBody CommunityCommentCreateRequestDto requestDto
    ) {
        CommunityCommentResponseDto response =
                commentService.createComment(postId, authorId, requestDto);

        return ResponseEntity.ok(response);
    }

    // 댓글 수정
    @PatchMapping("/comments/{commentId}")
    public ResponseEntity<CommunityCommentResponseDto> updateComment(
            @PathVariable Long commentId,
            @AuthenticationPrincipal String authorId,
            @RequestBody CommunityCommentUpdateRequestDto requestDto
    ) {
        CommunityCommentResponseDto response =
                commentService.updateComment(commentId, requestDto);

        return ResponseEntity.ok(response);
    }

    // 댓글 삭제
    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(
            @PathVariable Long commentId,
            @AuthenticationPrincipal String authorId
    ) {
        commentService.deleteComment(commentId);
        return ResponseEntity.noContent().build();
    }
}
