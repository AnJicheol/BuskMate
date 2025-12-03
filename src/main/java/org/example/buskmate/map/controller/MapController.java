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

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/map")
public class MapController {

    private final MapMarkerService mapMarkerService;

    /**
     * 현재 지도 bounds + 타입 필터 기반 마커 조회
     */
    @GetMapping("/markers")
    public List<MapMarkerResponseDto> getMarkersInBounds(
            @RequestParam double southWestLat,
            @RequestParam double southWestLng,
            @RequestParam double northEastLat,
            @RequestParam double northEastLng,
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
     * 지도 마커 등록
     */
    @PostMapping("/markers")
    public MapMarkerResponseDto createMarker(
            @RequestBody @Valid MapMarkerCreateRequestDto request
    ) {
        return mapMarkerService.createMarker(request);
    }

    /**
     * 지도 마커 삭제
     */
    @DeleteMapping("/markers/{markerId}")
    public void deleteMarker(
            @PathVariable Long markerId
    ) {
        mapMarkerService.deleteMarker(markerId);
    }

}
