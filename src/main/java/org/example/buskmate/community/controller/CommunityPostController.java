package org.example.buskmate.community.controller;

import lombok.RequiredArgsConstructor;
import org.example.buskmate.community.dto.crud.request.CreatePostRequest;
import org.example.buskmate.community.dto.crud.request.DeletePostRequest;
import org.example.buskmate.community.dto.crud.request.UpdatePostRequest;
import org.example.buskmate.community.dto.crud.response.CreatePostResponse;
import org.example.buskmate.community.dto.crud.response.PostIdResponse;
import org.example.buskmate.community.dto.crud.response.UpdatePostResponse;
import org.example.buskmate.community.service.CommunityPostService;
import org.example.buskmate.community.service.CommunityPostServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/community")
@RequiredArgsConstructor
public class CommunityPostController {

    private final CommunityPostService communityPostService;

    @PostMapping("/posts")
    public CreatePostResponse createPost(@RequestBody CreatePostRequest request){
        return null;
    }

    @GetMapping("/posts/{id}")
    public PostIdResponse getPostById(@PathVariable String id){
        return null;
    }

    @PatchMapping("/posts/{id}")
    public UpdatePostResponse updatePost(@PathVariable String id, @RequestBody UpdatePostRequest request){
        return null;
    }

    @DeleteMapping("/posts/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable String id){
        return null;
    }
}
