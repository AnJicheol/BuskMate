package org.example.buskmate.map.service;

import org.example.buskmate.map.dto.MapMarkerResponseDto;
import org.example.buskmate.map.dto.MapMarkerSearchRequestDto;

import java.util.List;

public interface MapMarkerQueryService {

    /**
     * 지도 bounds + 타입 필터 기반 마커 조회
     */
    List<MapMarkerResponseDto> getMarkersInBounds(MapMarkerSearchRequestDto request);
}
