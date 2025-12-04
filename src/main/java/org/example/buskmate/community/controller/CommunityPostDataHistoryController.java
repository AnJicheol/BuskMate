package org.example.buskmate.community.controller;

import lombok.RequiredArgsConstructor;
import org.example.buskmate.community.dto.CommunityPostDataHistoryResponseDto;
import org.example.buskmate.community.service.CommunityPostDataHistoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/community")
public class CommunityPostDataHistoryController {

    private final CommunityPostDataHistoryService historyService;

    // 특정 게시글의 히스토리 목록 조회
    @GetMapping("/posts/{postId}/histories")
    public ResponseEntity<List<CommunityPostDataHistoryResponseDto>> getPostHistories(
            @PathVariable Long postId
    ) {
        List<CommunityPostDataHistoryResponseDto> histories =
                historyService.getHistoriesByPostId(postId);

        return ResponseEntity.ok(histories);
    }
}
