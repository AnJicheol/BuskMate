package org.example.buskmate.band.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.buskmate.band.dto.band.*;
import org.example.buskmate.band.service.BandService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/bands")
@Tag(name = "Band API", description = "밴드 생성/조회/수정/비활성화 API")
public class BandController {

    private final BandService bandService;

    @Operation(
            summary = "밴드 생성",
            description = "로그인한 사용자를 리더로 하여 새로운 밴드를 생성합니다."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "밴드 생성 성공",
                    content = @Content(schema = @Schema(implementation = BandCreateResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "요청 값 검증 실패 (예: 밴드 이름 누락)",
                    content = @Content
            )
    })
    @PostMapping
    public ResponseEntity<BandCreateResponse> create(
            @Valid @RequestBody BandCreateRequest request,
            @Parameter(hidden = true)
            @AuthenticationPrincipal CustomUser user
    ) {
        BandCreateResponse response = bandService.create(request, user.getUserId());
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "밴드 전체 목록 조회",
            description = "활성 상태의 밴드들을 전체 목록으로 조회합니다. 밴드가 없으면 빈 배열([])을 반환합니다."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "밴드 목록 조회 성공",
                    content = @Content(schema = @Schema(implementation = BandListItemResponse.class))
            )
    })
    @GetMapping
    public ResponseEntity<List<BandListItemResponse>> getAll() {
        return ResponseEntity.ok(bandService.getAllBands());
    }

    @Operation(
            summary = "밴드 상세 조회",
            description = "bandId(외부 식별자)로 해당 밴드의 상세 정보를 조회합니다."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "밴드 상세 조회 성공",
                    content = @Content(schema = @Schema(implementation = BandDetailResponse.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "해당 bandId의 밴드가 존재하지 않음",
                    content = @Content
            )
    })
    @GetMapping("/{bandId}")
    public ResponseEntity<BandDetailResponse> getById(
            @Parameter(description = "밴드 외부 식별자", example = "01JH1ABCDXYZ...")
            @PathVariable String bandId
    ) {
        return ResponseEntity.ok(bandService.getByBandId(bandId));
    }

    @Operation(
            summary = "밴드 정보 수정",
            description = "bandId에 해당하는 밴드의 정보를 수정합니다."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "밴드 정보 수정 성공",
                    content = @Content(schema = @Schema(implementation = BandDetailResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "요청 값 검증 실패",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "해당 bandId의 밴드가 존재하지 않음",
                    content = @Content
            )
    })
    @PatchMapping("/{bandId}")
    public ResponseEntity<BandDetailResponse> updateBand(
            @Parameter(description = "밴드 외부 식별자", example = "01JH1ABCDXYZ...")
            @PathVariable String bandId,
            @RequestBody UpdateBandRequest request
    ) {
        BandDetailResponse response = bandService.updateBand(bandId, request);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "밴드 비활성화(삭제)",
            description = "bandId에 해당하는 밴드를 비활성화합니다. 실제 물리 삭제가 아닌 소프트 삭제(STATUS 변경) 방식일 수 있습니다."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "밴드 비활성화 성공 (응답 본문 없음)"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "해당 bandId의 밴드가 존재하지 않음",
                    content = @Content
            )
    })
    @DeleteMapping("/{bandId}")
    public ResponseEntity<Void> deleteBand(
            @Parameter(description = "밴드 외부 식별자", example = "01JH1ABCDXYZ...")
            @PathVariable String bandId
    ) {
        bandService.deactivate(bandId);
        return ResponseEntity.noContent().build();
    }
}