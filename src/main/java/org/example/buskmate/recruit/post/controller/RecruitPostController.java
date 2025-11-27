package org.example.buskmate.recruit.post.controller;

import lombok.RequiredArgsConstructor;
import org.example.buskmate.recruit.post.dto.CreateRecruitPostRequestDto;
import org.example.buskmate.recruit.post.dto.CreateRecruitPostResponseDto;
import org.example.buskmate.recruit.post.dto.RecruitPostDetailResponseDto;
import org.example.buskmate.recruit.post.service.RecruitPostService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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
}
