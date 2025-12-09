package org.example.buskmate.community.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.example.buskmate.community.dto.post.crud.request.*;
import org.example.buskmate.community.dto.post.crud.response.CommunityPostReadAllPostResponse;
import org.example.buskmate.community.dto.post.crud.response.CommunityPostReadPostResponse;
import org.example.buskmate.community.service.CommunityPostFacadeService;
import org.example.buskmate.community.service.CommunityPostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

//테스트할 때는 Principal잠깐 지우고 다시 달아두기
@RestController
@RequestMapping("/community")
@RequiredArgsConstructor
public class CommunityPostController {

    private final CommunityPostService communityPostService;
    private final CommunityPostFacadeService communityPostFacadeService;

    // 1. 게시글 생성
    @PostMapping("/posts/create")
    @Operation(
            summary = "게시글 생성",
            description = "게시글 제목과 내용을 게시합니다"
    )
    @ApiResponse(
            responseCode = "201",
            description =  "게시글 생성 완료"
    )

    public ResponseEntity<Void> createPost(
            @RequestBody CommunityPostCreatePostRequest request,
            @AuthenticationPrincipal String authorId
    ){
        communityPostService.createPost(request, authorId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    // 2. 전체 게시글 조회
    @GetMapping("/posts")
    @Operation(
            summary = "전체 게시글 조회",
            description = "모든 게시물들에 대해서 제목, 작성자, 조회수, 댓글수를 확인 할 수 있습니다."
    )
    @ApiResponse(
            responseCode = "200",
            description = "전체 게시물 조회 성공"
    )
    public ResponseEntity<Page<CommunityPostReadAllPostResponse>> getAllPost(
            Pageable pageable,
            @AuthenticationPrincipal String authorId
    ){
        Page<CommunityPostReadAllPostResponse> response = communityPostService.getAllPost(pageable, authorId);

        return ResponseEntity.ok(response);
    }
    // 3. 단일 게시글 조회
    @GetMapping("/posts/{postId}")
    @Operation(
            summary = "단일 게시글 조회",
            description = "제목, 내용, 댓글들을 확인 할 수 있습니다."
    )
    @ApiResponse(
            responseCode = "200",
            description = "단일 게시글 조회 성공",
            content = @Content(schema = @Schema(implementation = CommunityPostReadPostResponse.class))
    )
    public ResponseEntity<CommunityPostReadPostResponse> getPostId(
            @AuthenticationPrincipal String viewerId,
            @PathVariable Long postId
    ){
        return ResponseEntity.ok(communityPostFacadeService.getPostWithCommentAndView(viewerId, postId));
    }
    // 4. 게시글 수정
    // 생성, 수정, 삭제시의 comment 히스토리 호출하기
    @PatchMapping("/posts/edit/{postId}")
    @Operation(
            summary = "단일 게시글 수정",
            description = "동일한 사용자만이 게시글을 수정할 수 있습니다. 제목과 내용을 변경합니다."
    )
    @ApiResponse(
            responseCode = "200",
            description = "application/json"
    )
    public ResponseEntity<Void> updatePost(
            @AuthenticationPrincipal String authorId,
            @PathVariable Long postId,
            @RequestBody CommunityPostUpdatePostRequest request
    ){
        communityPostService.updatePost(authorId, postId, request);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
    // 5. 게시글 삭제
    @DeleteMapping("/posts/delete/{id}")
    @Operation(
            summary = "단일 게시글 삭제",
            description = "동일한 사용자만이 게시글을 삭제합니다."
    )
    @ApiResponse(
            responseCode = "200",
            description = "application/json"
    )
    public ResponseEntity<Void> deletePost(
            @AuthenticationPrincipal String authorId,
            @PathVariable Long id
    ){
        communityPostService.deletePost(authorId, id);
        return ResponseEntity.noContent().build();
    }
}
