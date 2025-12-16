package org.example.buskmate.community.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.buskmate.community.dto.CommunityCommentCreateRequestDto;
import org.example.buskmate.community.dto.CommunityCommentResponseDto;
import org.example.buskmate.community.dto.CommunityCommentUpdateRequestDto;
import org.example.buskmate.community.service.CommunityCommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 커뮤니티 댓글 API 컨트롤러
 * - 특정 게시글의 댓글 조회/작성/수정/삭제 기능을 제공한다.
 * - 인증된 사용자(@AuthenticationPrincipal)를 댓글 작성자(author)로 사용한다.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/community")
@Tag(name = "Community Comments", description = "커뮤니티 게시글 댓글 API")
public class CommunityCommentController {

    private final CommunityCommentService commentService;

    /**
     * 특정 게시글의 활성 댓글 목록을 시간순으로 조회한다.
     */
    @GetMapping("/posts/{postId}/comments")
    @Operation(
            summary = "댓글 목록 조회",
            description = "특정 커뮤니티 게시글에 대한 활성 댓글 목록을 시간 순으로 조회합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "댓글 목록 조회 성공"),
            @ApiResponse(responseCode = "404", description = "게시글을 찾을 수 없음")
    })
    public ResponseEntity<List<CommunityCommentResponseDto>> getComments(
            @Parameter(description = "커뮤니티 게시글 ID", example = "1")
            @PathVariable Long postId
    ) {
        return ResponseEntity.ok(commentService.getCommentsByPostId(postId));
    }

    /**
     * 특정 게시글에 댓글을 작성한다.
     * - authorId는 인증 Principal에서 주입받아 작성자로 사용한다.
     */
    @PostMapping("/posts/{postId}/comments")
    @Operation(
            summary = "댓글 작성",
            description = "특정 커뮤니티 게시글에 댓글을 작성합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "댓글 작성 성공"),
            @ApiResponse(responseCode = "404", description = "게시글을 찾을 수 없음"),
            @ApiResponse(responseCode = "401", description = "인증 실패")
    })
    public ResponseEntity<CommunityCommentResponseDto> createComment(
            @Parameter(description = "커뮤니티 게시글 ID", example = "1")
            @PathVariable Long postId,

            @Parameter(hidden = true) // Swagger UI 에서 숨김
            @AuthenticationPrincipal String authorId,

            @RequestBody CommunityCommentCreateRequestDto requestDto
    ) {
        CommunityCommentResponseDto response =
                commentService.createComment(postId, authorId, requestDto);

        return ResponseEntity.ok(response);
    }

    /**
     * 작성한 댓글의 내용을 수정한다.
     * - 낙관적 락(@Version) 기반으로 동시 수정 충돌을 감지할 수 있다.
     */
    @PatchMapping("/comments/{commentId}")
    @Operation(
            summary = "댓글 수정",
            description = "작성한 댓글의 내용을 수정합니다. (낙관적 락으로 버전 관리)"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "댓글 수정 성공"),
            @ApiResponse(responseCode = "404", description = "댓글을 찾을 수 없음"),
            @ApiResponse(responseCode = "401", description = "본인이 아닌 경우 또는 인증 실패")
    })
    public ResponseEntity<CommunityCommentResponseDto> updateComment(
            @Parameter(description = "댓글 ID", example = "1")
            @PathVariable Long commentId,

            @Parameter(hidden = true)
            @AuthenticationPrincipal String authorId,

            @RequestBody CommunityCommentUpdateRequestDto requestDto
    ) {
        CommunityCommentResponseDto response =
                commentService.updateComment(commentId, requestDto);

        return ResponseEntity.ok(response);
    }

    /**
     * 댓글을 소프트 삭제(상태 플래그 변경)한다.
     */
    @DeleteMapping("/comments/{commentId}")
    @Operation(
            summary = "댓글 삭제",
            description = "댓글을 소프트 삭제(DELETE 플래그 변경)합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "댓글 삭제 성공 (내용 없음)"),
            @ApiResponse(responseCode = "404", description = "댓글을 찾을 수 없음"),
            @ApiResponse(responseCode = "401", description = "본인이 아닌 경우 또는 인증 실패")
    })
    public ResponseEntity<Void> deleteComment(
            @Parameter(description = "댓글 ID", example = "1")
            @PathVariable Long commentId,

            @Parameter(hidden = true)
            @AuthenticationPrincipal String authorId
    ) {
        commentService.deleteComment(commentId);
        return ResponseEntity.noContent().build();
    }
}