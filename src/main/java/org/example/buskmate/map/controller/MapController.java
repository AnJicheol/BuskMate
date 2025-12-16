package org.example.buskmate.map.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.buskmate.map.domain.MarkerType;
import org.example.buskmate.map.dto.MapMarkerCreateRequestDto;
import org.example.buskmate.map.dto.MapMarkerResponseDto;
import org.example.buskmate.map.dto.MapMarkerSearchRequestDto;
import org.example.buskmate.map.service.MapMarkerService;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

/**
 * 지도 마커 API 컨트롤러
 * - 지도 화면에서 마커 조회/등록/삭제 요청을 처리한다.
 * - bounds(현재 지도 영역) + 타입 필터 기반 조회를 지원한다.
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/map")
@Tag(name = "Map Markers", description = "지도 마커 API")
public class MapController {

    private final MapMarkerService mapMarkerService;

    /**
     * 지도 bounds(남서/북동 좌표)와 타입 필터를 기반으로 마커 목록을 조회한다.
     * - types가 null/empty면 전체 타입을 조회한다.
     */
    @GetMapping("/markers")
    @Operation(
            summary = "지도 마커 조회",
            description = "현재 지도 bounds(남서/북동 좌표)와 마커 타입 필터를 기준으로 마커 목록을 조회합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "마커 목록 조회 성공")
    })
    public List<MapMarkerResponseDto> getMarkersInBounds(
            @Parameter(description = "남서쪽 위도(SW latitude)", example = "37.55")
            @RequestParam double southWestLat,

            @Parameter(description = "남서쪽 경도(SW longitude)", example = "126.97")
            @RequestParam double southWestLng,

            @Parameter(description = "북동쪽 위도(NE latitude)", example = "37.60")
            @RequestParam double northEastLat,

            @Parameter(description = "북동쪽 경도(NE longitude)", example = "127.05")
            @RequestParam double northEastLng,

            @Parameter(
                    description = "필터링할 마커 타입 집합 (예: BAND,BUSKING). 비우면 전체 타입 조회",
                    example = "BAND"
            )
            @RequestParam(required = false) Set<MarkerType> types
    ) {
        MapMarkerSearchRequestDto request = MapMarkerSearchRequestDto.builder()
                .southWestLat(southWestLat)
                .southWestLng(southWestLng)
                .northEastLat(northEastLat)
                .northEastLng(northEastLng)
                .types(types) // null/empty면 서비스에서 전체 타입 허용
                .build();

        return mapMarkerService.getMarkersInBounds(request);
    }

    /**
     * 위도/경도, 타입, 제목 정보를 기반으로 새로운 지도 마커를 등록한다.
     * - 요청 DTO 검증(@Valid) 실패 시 400을 반환한다.
     */
    @PostMapping("/markers")
    @Operation(
            summary = "지도 마커 등록",
            description = "위도/경도, 타입, 제목 정보를 기반으로 새로운 지도 마커를 등록합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "마커 등록 성공"),
            @ApiResponse(responseCode = "400", description = "요청 값 검증 실패")
    })
    public MapMarkerResponseDto createMarker(
            @RequestBody @Valid MapMarkerCreateRequestDto request
    ) {
        return mapMarkerService.createMarker(request);
    }

    /**
     * 마커 ID로 특정 지도 마커를 삭제한다.
     * - 대상이 없으면 404를 반환한다.
     */
    @DeleteMapping("/markers/{markerId}")
    @Operation(
            summary = "지도 마커 삭제",
            description = "마커 ID로 특정 지도 마커를 삭제합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "마커 삭제 성공 (응답 본문 없음)"),
            @ApiResponse(responseCode = "404", description = "마커를 찾을 수 없음")
    })
    public void deleteMarker(
            @Parameter(description = "마커 ID", example = "1")
            @PathVariable Long markerId
    ) {
        mapMarkerService.deleteMarker(markerId);
    }


}
