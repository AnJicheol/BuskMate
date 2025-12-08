package org.example.buskmate.map.service;

import org.example.buskmate.map.dto.MapMarkerCreateRequestDto;
import org.example.buskmate.map.dto.MapMarkerResponseDto;
import org.example.buskmate.map.dto.MapMarkerSearchRequestDto;

import java.util.List;

public interface MapMarkerService {

    /**
     * 지도 bounds + 타입 필터 기반 마커 조회
     */
    List<MapMarkerResponseDto> getMarkersInBounds(MapMarkerSearchRequestDto request);


    /**
     * 지도 마커 등록
     */
    MapMarkerResponseDto createMarker(MapMarkerCreateRequestDto request);

    /**
     * 지도 마커 삭제
     */
    void deleteMarker(Long markerId);
}
