package org.example.buskmate.band.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.example.buskmate.band.dto.CustomUser;
import org.example.buskmate.band.dto.recruitapplication.RecruitApplicationListItemDto;
import org.example.buskmate.band.dto.recruitapplication.RecruitApplyResponseDto;
import org.example.buskmate.band.service.RecruitApplicationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/recruit")
public class RecruitApplicationController {
    private final RecruitApplicationService recruitApplicationService;
    @Operation(
            summary = "모집 글 지원",
            description = "로그인한 사용자가 특정 모집 글에 지원합니다. " +
                    "이미 지원한 경우나 모집이 마감된 경우 오류가 발생합니다."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "지원 성공",
                    content = @Content(schema = @Schema(implementation = RecruitApplyResponseDto.class))
            ),
            @ApiResponse(responseCode = "400", description = "잘못된 요청(지원 불가 상태 등)"),
            @ApiResponse(responseCode = "401", description = "인증 필요"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 모집 글"),
            @ApiResponse(responseCode = "409", description = "이미 지원한 모집 글")
    })
    @PostMapping("/{postId}/apply")
    public ResponseEntity<RecruitApplyResponseDto> apply(@PathVariable String postId, @AuthenticationPrincipal CustomUser user){
        RecruitApplyResponseDto response = recruitApplicationService.apply(postId, user.getUserId());

        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "지원 취소",
            description = "사용자가 본인이 지원한 모집 글의 지원을 취소합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "취소 성공"),
            @ApiResponse(responseCode = "401", description = "인증 필요"),
            @ApiResponse(responseCode = "403", description = "본인의 지원이 아님"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 지원 내역")
    })
    @DeleteMapping("/apply/{applicationId}")
    public ResponseEntity<Void> delete(@PathVariable String applicationId, @AuthenticationPrincipal CustomUser user){
        recruitApplicationService.delete(applicationId, user.getUserId());
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "지원 수락",
            description = "밴드 리더가 특정 모집 글에 지원한 사용자를 수락합니다. " +
                    "해당 모집 글의 소유 밴드 리더만 호출할 수 있습니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "수락 성공"),
            @ApiResponse(responseCode = "401", description = "인증 필요"),
            @ApiResponse(responseCode = "403", description = "해당 모집 글의 리더가 아님"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 지원 내역"),
            @ApiResponse(responseCode = "409", description = "이미 처리된 지원(수락/거절 완료)")
    })
    @PatchMapping("/{applicationId}/accept")
    public ResponseEntity<Void> accept(@PathVariable String applicationId, @AuthenticationPrincipal CustomUser user){
        recruitApplicationService.accept(applicationId, user.getUserId());
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "지원 거절",
            description = "밴드 리더가 특정 모집 글에 지원한 사용자를 거절합니다. " +
                    "해당 모집 글의 소유 밴드 리더만 호출할 수 있습니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "거절 성공"),
            @ApiResponse(responseCode = "401", description = "인증 필요"),
            @ApiResponse(responseCode = "403", description = "해당 모집 글의 리더가 아님"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 지원 내역"),
            @ApiResponse(responseCode = "409", description = "이미 처리된 지원(수락/거절 완료)")
    })
    @PatchMapping("/{applicationId}/reject")
    public ResponseEntity<Void> reject(@PathVariable String applicationId, @AuthenticationPrincipal CustomUser user){
        recruitApplicationService.reject(applicationId, user.getUserId());
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "모집 글 지원 목록 조회",
            description = "특정 모집 글에 들어온 지원 목록을 페이지네이션 형태로 조회합니다. " +
                    "해당 모집 글의 밴드 리더만 조회할 수 있습니다.\n\n" +
                    "- page: 0부터 시작하는 페이지 인덱스 (기본값 0)\n" +
                    "- size: 페이지 당 항목 수 (기본값 10)"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "조회 성공",
                    content = @Content(schema = @Schema(implementation = RecruitApplicationListItemDto.class))
            ),
            @ApiResponse(responseCode = "401", description = "인증 필요"),
            @ApiResponse(responseCode = "403", description = "해당 모집 글의 리더가 아님"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 모집 글")
    })
    @GetMapping("/{postId}/applications")
    public ResponseEntity<Page<RecruitApplicationListItemDto>> getApplications(@PathVariable String postId,@AuthenticationPrincipal CustomUser user,
                                                                               @RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "appliedAt"));

        Page<RecruitApplicationListItemDto> result =
                recruitApplicationService.getApplications(postId, user.getUserId(), pageable);

        return ResponseEntity.ok(result);
    }
}
