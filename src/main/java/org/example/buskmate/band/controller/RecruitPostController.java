package org.example.buskmate.band.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.example.buskmate.band.dto.CustomUser;
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

    @Operation(
            summary = "모집 글 생성",
            description = "로그인한 사용자가 새로운 팀원 모집 글을 생성합니다."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "생성 성공",
                    content = @Content(schema = @Schema(implementation = CreateRecruitPostResponseDto.class))
            ),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 데이터"),
            @ApiResponse(responseCode = "401", description = "인증 필요"),
            @ApiResponse(responseCode = "404", description = "밴드를 찾을 수 없음"),
            @ApiResponse(responseCode = "403", description = "해당 밴드 리더가 아님")
    })
    @PostMapping
    public ResponseEntity<CreateRecruitPostResponseDto> createRecruitPost(@RequestBody CreateRecruitPostRequestDto req, @AuthenticationPrincipal CustomUser user) {
        return ResponseEntity.ok(
                recruitPostService.create(req, user.getUserId()));
    }
    @Operation(
            summary = "모집 글 상세 조회",
            description = "모집 글 ID로 상세 정보를 조회합니다."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "조회 성공",
                    content = @Content(schema = @Schema(implementation = RecruitPostDetailResponseDto.class))
            ),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 모집 글")
    })
    @GetMapping("/{postId}")
    public ResponseEntity<RecruitPostDetailResponseDto> getDetail(@PathVariable String postId){
        return ResponseEntity.ok(
                recruitPostService.getDetail(postId));
    }

    @Operation(
            summary = "활성 상태 모집 글 목록 조회",
            description = "현재 모집 중(활성 상태)인 모집 글 목록을 조회합니다."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "조회 성공",
                    content = @Content(
                            array = @ArraySchema(schema = @Schema(implementation = RecruitPostListDto.class))
                    )
            )
    })
    @GetMapping("/list/active")
    public ResponseEntity<List<RecruitPostListDto>> getActiveList(){
        return ResponseEntity.ok(
                recruitPostService.getActiveList());
    }

    @Operation(
            summary = "모집 글 수정",
            description = "모집 글 작성자(밴드 리더)가 제목/내용/모집 조건 등을 수정합니다."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "수정 성공",
                    content = @Content(schema = @Schema(implementation = RecruitPostDetailResponseDto.class))
            ),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 데이터"),
            @ApiResponse(responseCode = "401", description = "인증 필요"),
            @ApiResponse(responseCode = "403", description = "해당 모집 글을 수정할 권한 없음"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 모집 글")
    })
    @PatchMapping("/{postId}")
    public ResponseEntity<RecruitPostDetailResponseDto> updateRecruitPost(@PathVariable String postId, @RequestBody UpdateRecruitPostRequestDto req,@AuthenticationPrincipal CustomUser user){
        return ResponseEntity.ok(
                recruitPostService.update(postId, req, user.getUserId()));
    }

    @Operation(
            summary = "모집 글 마감",
            description = "밴드 리더가 더 이상 지원을 받지 않도록 모집 글 상태를 마감으로 변경합니다."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "마감 상태 변경 성공",
                    content = @Content(schema = @Schema(implementation = RecruitPostStatusResponseDto.class))
            ),
            @ApiResponse(responseCode = "401", description = "인증 필요"),
            @ApiResponse(responseCode = "403", description = "해당 모집 글을 마감할 권한 없음"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 모집 글")
    })
    @PatchMapping("/{postId}/close")
    public ResponseEntity<RecruitPostStatusResponseDto> closeRecruitPost(@PathVariable String postId, @AuthenticationPrincipal CustomUser user){
        return ResponseEntity.ok(
                recruitPostService.close(postId, user.getUserId()));
    }

    @Operation(
            summary = "모집 글 삭제(비활성화)",
            description = "밴드 리더가 모집 글을 삭제(또는 비활성화)합니다. " +
                    "실제 물리 삭제가 아닌 상태 변경일 수 있습니다."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "삭제(비활성화) 상태 변경 성공",
                    content = @Content(schema = @Schema(implementation = RecruitPostStatusResponseDto.class))
            ),
            @ApiResponse(responseCode = "401", description = "인증 필요"),
            @ApiResponse(responseCode = "403", description = "해당 모집 글을 삭제할 권한 없음"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 모집 글")
    })
    @PatchMapping("/{postId}/delete")
    public ResponseEntity<RecruitPostStatusResponseDto> deleteRecruitPost(@PathVariable String postId, @AuthenticationPrincipal CustomUser user){
        return ResponseEntity.ok(
                recruitPostService.delete(postId, user.getUserId()));
    }
}
