package org.example.buskmate.community.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.example.buskmate.auth.dto.UsersPrincipal;
import org.example.buskmate.community.dto.post.crud.request.*;
import org.example.buskmate.community.dto.post.crud.response.CommunityPostReadAllPostResponse;
import org.example.buskmate.community.dto.post.crud.response.CommunityPostReadPostResponse;
import org.example.buskmate.community.service.CommunityPostDeleteFacadeService;
import org.example.buskmate.community.service.CommunityPostFacadeService;
import org.example.buskmate.community.service.CommunityPostService;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

/**
 * 커뮤니티 게시글 API 컨트롤러
 * - 게시글 생성/전체 조회/단일 조회/수정/삭제 기능을 제공한다.
 * - 단일 조회는 Facade를 통해 조회수 기록 및 댓글 포함 응답을 함께 처리한다.
 * - 삭제는 Facade를 통해 댓글 소프트 삭제까지 함께 처리한다.
 */
@RestController
@RequestMapping("/community")
@RequiredArgsConstructor
public class CommunityPostController {

    private final CommunityPostService communityPostService;
    private final CommunityPostFacadeService communityPostFacadeService;
    private final CommunityPostDeleteFacadeService communityPostDeleteFacadeService;

    /**
     * 게시글 제목/내용으로 게시글을 생성한다.
     */
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
            @AuthenticationPrincipal UsersPrincipal user
            ){
        communityPostService.createPost(request, user.getUserId());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 전체 게시글을 페이징 형태로 조회한다.
     * - 목록에서는 제목/작성자/조회수/댓글수 등의 요약 정보를 제공한다.
     */
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
            @ParameterObject Pageable pageable,
            @AuthenticationPrincipal UsersPrincipal user
    ){
        Page<CommunityPostReadAllPostResponse> response = communityPostService.getAllPost(pageable, user.getUserId());

        return ResponseEntity.ok(response);
    }

    /**
     * 단일 게시글을 조회한다.
     * - 조회 시 조회수 기록/집계 및 댓글 목록 포함 응답을 Facade에서 처리한다.
     */
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
            @AuthenticationPrincipal UsersPrincipal user,
            @PathVariable Long postId
    ){
        return ResponseEntity.ok(communityPostFacadeService.getPostWithCommentAndView(user.getUserId(), postId));
    }



    /**
     * 게시글을 수정한다.
     * - 작성자만 수정 가능하며 제목/내용을 변경한다.
     */
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
            @AuthenticationPrincipal UsersPrincipal user,
            @PathVariable Long postId,
            @RequestBody CommunityPostUpdatePostRequest request
    ){
        communityPostService.updatePost(user.getUserId(), postId, request);
        return ResponseEntity.status(HttpStatus.OK).build();
    }


    /**
     * 게시글을 삭제한다.
     * - 게시글 삭제와 함께 해당 게시글의 댓글 소프트 삭제를 Facade에서 함께 처리한다.
     */

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
            @AuthenticationPrincipal UsersPrincipal user,
            @PathVariable Long id
    ){
        communityPostDeleteFacadeService.deletePostWithComment(user.getUserId(), id);
        return ResponseEntity.noContent().build();
    }
}
