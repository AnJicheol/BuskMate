package org.example.buskmate.map.controller;

import lombok.RequiredArgsConstructor;
import org.example.buskmate.map.domain.MarkerType;
import org.example.buskmate.map.dto.MapMarkerResponseDto;
import org.example.buskmate.map.dto.MapMarkerSearchRequestDto;
import org.example.buskmate.map.service.MapMarkerQueryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/map")
public class MapController {

    private final MapMarkerQueryService mapMarkerQueryService;

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

        return mapMarkerQueryService.getMarkersInBounds(request);
    }

}
