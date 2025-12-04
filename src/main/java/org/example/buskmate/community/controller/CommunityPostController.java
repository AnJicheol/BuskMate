package org.example.buskmate.community.controller;

import lombok.RequiredArgsConstructor;
import org.example.buskmate.community.dto.post.crud.request.*;
import org.example.buskmate.community.dto.post.crud.response.CommunityPostReadAllPostResponse;
import org.example.buskmate.community.dto.post.crud.response.CommunityPostReadPostResponse;
import org.example.buskmate.community.service.CommunityPostService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/community")
@RequiredArgsConstructor
public class CommunityPostController {

    private final CommunityPostService communityPostService;

    // 1. 게시글 생성
    @PostMapping("/posts/create")
    public ResponseEntity<Void> createPost(@RequestBody CommunityPostCreatePostRequest request){
        communityPostService.createPost(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    // 2. 전체 게시글 조회
    @GetMapping("/posts")
    public ResponseEntity<Page<CommunityPostReadAllPostResponse>> getAllPost(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "false") boolean desc
    ){
        CommunityPostReadAllPostRequest request = new CommunityPostReadAllPostRequest(
                null,
                null,
                page,
                size,
                sortBy,
                desc
        );

        Page<CommunityPostReadAllPostResponse> response = communityPostService.getAllPost(request);
        return ResponseEntity.ok(response);
    }
    // 3. 특정 게시글 조회
    @GetMapping("/posts/{id}")
    public ResponseEntity<CommunityPostReadPostResponse> getPostId(@PathVariable Integer id, @RequestBody CommunityPostReadPostRequest request){
        communityPostService.getPostId(id, request);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
    // 4. 게시글 수정
    @PatchMapping("/posts/{id}")
    public ResponseEntity<Void> updatePost(@PathVariable Integer id, @RequestBody CommunityPostUpdatePostRequest request){
        communityPostService.updatePost(id, request);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
    // 5. 게시글 삭제
    @DeleteMapping("/posts/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Integer id, @RequestBody CommunityPostDeletePostRequest request){
        communityPostService.deletePost(id, request);
        return ResponseEntity.noContent().build();
    }
}
