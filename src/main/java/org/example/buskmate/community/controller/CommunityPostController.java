package org.example.buskmate.community.controller;

import lombok.RequiredArgsConstructor;
import org.example.buskmate.community.dto.post.crud.request.*;
import org.example.buskmate.community.dto.post.crud.response.CommunityPostReadAllPostResponse;
import org.example.buskmate.community.dto.post.crud.response.CommunityPostReadPostResponse;
import org.example.buskmate.community.service.CommunityPostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/community")
@RequiredArgsConstructor
public class CommunityPostController {

    private final CommunityPostService communityPostService;

    // 1. 게시글 생성
    @PostMapping("/posts/create")
    public ResponseEntity<Void> createPost(
            @RequestBody CommunityPostCreatePostRequest request,
            @AuthenticationPrincipal String authorId
    ){
        communityPostService.createPost(request, authorId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    // 2. 전체 게시글 조회
    @GetMapping("/posts")
    public ResponseEntity<Page<CommunityPostReadAllPostResponse>> getAllPost(
            Pageable pageable,
            @AuthenticationPrincipal String authorId,
            CommunityPostReadAllPostRequest request){

        Page<CommunityPostReadAllPostResponse> response = communityPostService.getAllPost(pageable, authorId, request);

        return ResponseEntity.ok(response);
    }
    // 3. 특정 게시글 조회
    // api 만 보고 어떤 동작하는지 알 수 있게 수정
    @GetMapping("/posts/{postId}")
    public ResponseEntity<CommunityPostReadPostResponse> getPostId(
            @AuthenticationPrincipal String viewerId,
            @PathVariable Long postId,
            @RequestBody CommunityPostReadPostRequest request){
        CommunityPostReadPostResponse response = communityPostService.getPostId(viewerId, postId, request);

        return ResponseEntity.ok(response);
    }
    // 4. 게시글 수정
    // 생성, 수정, 삭제시의 수호님 히스토리 호출하기
    @PatchMapping("/posts/edit/{postId}")
    public ResponseEntity<Void> updatePost(
            @AuthenticationPrincipal String authorId,
            @RequestParam Long postId,
            @RequestBody CommunityPostUpdatePostRequest request
    ){
        communityPostService.updatePost(authorId, postId, request);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
    // 5. 게시글 삭제
    @DeleteMapping("/posts/delete/{id}")
    public ResponseEntity<Void> deletePost(
            @AuthenticationPrincipal String authorId,
            @PathVariable Long id
    ){
        communityPostService.deletePost(authorId, id);
        return ResponseEntity.noContent().build();
    }
}
