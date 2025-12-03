package org.example.buskmate.community.controller;

import lombok.RequiredArgsConstructor;
import org.example.buskmate.community.dto.crud.request.*;
import org.example.buskmate.community.dto.crud.response.ReadAllPostResponse;
import org.example.buskmate.community.dto.crud.response.ReadPostResponse;
import org.example.buskmate.community.service.CommunityPostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/community")
@RequiredArgsConstructor
public class CommunityPostController {

    private final CommunityPostService communityPostService;

    @PostMapping("/posts/create")
    public ResponseEntity<Void> createPost(@RequestBody CreatePostRequest request){
        communityPostService.createPost(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/posts")
    public ResponseEntity<List<ReadAllPostResponse>> getAllPost(@RequestBody ReadAllPostRequest request){
        communityPostService.getAllPost(request);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/posts/{id}")
    public ResponseEntity<ReadPostResponse> getPostId(@PathVariable Integer id, @RequestBody ReadPostRequest request){
        communityPostService.getPostId(id, request);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PatchMapping("/posts/{id}")
    public ResponseEntity<Void> updatePost(@PathVariable Integer id, @RequestBody UpdatePostRequest request){
        communityPostService.updatePost(id, request);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/posts/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Integer id, @RequestBody DeletePostRequest request){
        communityPostService.deletePost(id, request);
        return ResponseEntity.noContent().build();
    }
}
