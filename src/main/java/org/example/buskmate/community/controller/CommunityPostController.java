package org.example.buskmate.community.controller;

import lombok.RequiredArgsConstructor;
import org.example.buskmate.community.dto.crud.request.*;
import org.example.buskmate.community.dto.crud.response.ReadAllPostResponse;
import org.example.buskmate.community.dto.crud.response.ReadPostResponse;
import org.example.buskmate.community.service.CommunityPostService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/community")
@RequiredArgsConstructor
public class CommunityPostController {

    private final CommunityPostService communityPostService;

    // 1. 게시글 생성
    @PostMapping("/posts/create")
    public ResponseEntity<Void> createPost(@RequestBody CreatePostRequest request){
        communityPostService.createPost(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    // 2. 전체 게시글 조회
    @GetMapping("/posts")
    public ResponseEntity<Page<ReadAllPostResponse>> getAllPost(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "false") boolean desc
    ){
        ReadAllPostRequest request = new ReadAllPostRequest(
                null,
                null,
                page,
                size,
                sortBy,
                desc
        );

        Page<ReadAllPostResponse> response = communityPostService.getAllPost(request);
        return ResponseEntity.ok(response);
    }
    // 3. 특정 게시글 조회
    @GetMapping("/posts/{id}")
    public ResponseEntity<ReadPostResponse> getPostId(@PathVariable Integer id, @RequestBod ReadPostRequest request){
        communityPostService.getPostId(id, request);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
    // 4. 게시글 수정
    @PatchMapping("/posts/{id}")
    public ResponseEntity<Void> updatePost(@PathVariable Integer id, @RequestBody UpdatePostRequest request){
        communityPostService.updatePost(id, request);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
    // 5. 게시글 삭제
    @DeleteMapping("/posts/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Integer id, @RequestBody DeletePostRequest request){
        communityPostService.deletePost(id, request);
        return ResponseEntity.noContent().build();
    }
}
