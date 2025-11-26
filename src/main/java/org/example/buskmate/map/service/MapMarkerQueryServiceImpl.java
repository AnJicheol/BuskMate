package org.example.buskmate.map.service;

import lombok.RequiredArgsConstructor;
import org.example.buskmate.map.domain.MapMarker;
import org.example.buskmate.map.domain.MarkerType;
import org.example.buskmate.map.dto.MapMarkerResponseDto;
import org.example.buskmate.map.dto.MapMarkerSearchRequestDto;
import org.example.buskmate.map.repository.MapMarkerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MapMarkerQueryServiceImpl implements MapMarkerQueryService{

    private final MapMarkerRepository mapMarkerRepository;

    @Override
    public List<MapMarkerResponseDto> getMarkersInBounds(MapMarkerSearchRequestDto request) {

        // bounds 기준으로 db조회하기
        List<MapMarker> markers = mapMarkerRepository.findInBounds(
                request.getSouthWestLat(),
                request.getNorthEastLat(),
                request.getSouthWestLng(),
                request.getNorthEastLng()
        );

        // 타입 필터링
        Set<MarkerType> types = request.getTypes();
        boolean hasTypeFilter = types != null && !types.isEmpty();

        return markers.stream()
                .filter(marker -> !hasTypeFilter || types.contains(marker.getMarkerType()))
                .map(MapMarkerResponseDto::from)
                .toList();

    }
}
