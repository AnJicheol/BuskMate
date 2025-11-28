package org.example.buskmate.band.controller;

import lombok.RequiredArgsConstructor;
import org.example.buskmate.band.dto.recruitpost.*;
import org.example.buskmate.band.service.RecruitPostService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/recruit")
public class RecruitPostController {
    private final RecruitPostService recruitPostService;
    @PostMapping
    public ResponseEntity<CreateRecruitPostResponseDto> createRecruitPost(@RequestBody CreateRecruitPostRequestDto req, @AuthenticationPrincipal CustomUser user) {
        return ResponseEntity.ok(
                recruitPostService.create(req, user.getUserId()));
    }

    @GetMapping("/{postId}")
    public ResponseEntity<RecruitPostDetailResponseDto> getDetail(@PathVariable String postId){
        return ResponseEntity.ok(
                recruitPostService.getDetail(postId));
    }

    @GetMapping("/list/active")
    public ResponseEntity<List<RecruitPostListDto>> getActiveList(){
        return ResponseEntity.ok(
                recruitPostService.getActiveList());
    }

    @PatchMapping("/{postId}")
    public ResponseEntity<RecruitPostDetailResponseDto> updateRecruitPost(@PathVariable String postId, @RequestBody UpdateRecruitPostRequestDto req,@AuthenticationPrincipal CustomUser user){
        return ResponseEntity.ok(
                recruitPostService.update(postId, req, user.getUserId()));
    }

    @PatchMapping("/{postId}/close")
    public RecruitPostStatusResponseDto closeRecruitPost(@PathVariable String postId, @AuthenticationPrincipal CustomUser user){
        return ResponseEntity.ok(
                recruitPostService.close(postId, user.getUserId()));
    }

    @PatchMapping("/{postId}/delete")
    public RecruitPostStatusResponseDto deleteRecruitPost(@PathVariable String postId, @AuthenticationPrincipal CustomUser user){
        return ResponseEntity.ok(
                recruitPostService.delete(postId, user.getUserId()));
    }
}
