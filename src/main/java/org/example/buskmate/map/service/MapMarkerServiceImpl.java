package org.example.buskmate.map.service;

import lombok.RequiredArgsConstructor;
import org.example.buskmate.map.domain.MapLocation;
import org.example.buskmate.map.domain.MapMarker;
import org.example.buskmate.map.domain.MarkerType;
import org.example.buskmate.map.dto.MapMarkerCreateRequestDto;
import org.example.buskmate.map.dto.MapMarkerResponseDto;
import org.example.buskmate.map.dto.MapMarkerSearchRequestDto;
import org.example.buskmate.map.repository.MapMarkerRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MapMarkerServiceImpl implements MapMarkerService {

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

    @Transactional
    @Override
    public MapMarkerResponseDto createMarker(MapMarkerCreateRequestDto request) {
        // 위치 엔티티 생성
        MapLocation location = MapLocation.of(
                request.getLat(),
                request.getLng()
        );

        // 마커 엔티티 생성
        MapMarker marker = MapMarker.of(
                request.getMarkerType(),
                location,
                request.getTitle(),
                request.getSummary()
        );

        // 저장
        MapMarker saved = mapMarkerRepository.save(marker);

        // DTO로 변환해서 반환
        return MapMarkerResponseDto.from(saved);
    }

    @Transactional
    @Override
    public void deleteMarker(Long markerId) {
        MapMarker marker = mapMarkerRepository.findById(markerId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "MapMarker not found. id=" + markerId
                ));

        mapMarkerRepository.delete(marker);

    }

}
