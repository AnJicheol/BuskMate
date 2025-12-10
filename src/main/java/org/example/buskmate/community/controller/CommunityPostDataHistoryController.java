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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/community")
@Tag(name = "Community Post Histories", description = "커뮤니티 게시글 데이터 히스토리 API")
public class CommunityPostDataHistoryController {

    private final CommunityPostDataHistoryService historyService;

    // 특정 게시글의 히스토리 목록 조회
    @GetMapping("/posts/{postId}/histories")
    @Operation(
            summary = "게시글 히스토리 목록 조회",
            description = "게시글 수정 시 저장된 버전별 히스토리 목록을 최신순으로 조회합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "히스토리 목록 조회 성공"),
            @ApiResponse(responseCode = "404", description = "게시글을 찾을 수 없음")
    })
    public ResponseEntity<List<CommunityPostDataHistoryResponseDto>> getPostHistories(
            @Parameter(description = "커뮤니티 게시글 ID", example = "1")
            @PathVariable Long postId
    ) {
        List<CommunityPostDataHistoryResponseDto> histories =
                historyService.getHistoriesByPostId(postId);

        return ResponseEntity.ok(histories);
    }
}
