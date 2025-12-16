package org.example.buskmate.map.service;

import lombok.RequiredArgsConstructor;
import org.example.buskmate.map.domain.MapLocation;
import org.example.buskmate.map.domain.MapMarker;
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

/**
 * 지도 마커 서비스 구현체
 * - 레포지토리를 이용해 마커 조회/등록/삭제를 수행한다.
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MapMarkerServiceImpl implements MapMarkerService {

    private final MapMarkerRepository mapMarkerRepository;

    /**
     * bounds 영역으로 DB 조회 후, 필요 시 타입 필터를 적용하여 응답 DTO 목록으로 반환한다.
     */
    @Override
    public List<MapMarkerResponseDto> getMarkersInBounds(MapMarkerSearchRequestDto request) {

        // bounds 기준으로 db조회하기
        List<MapMarker> markers = mapMarkerRepository.findInBounds(
                request.getSouthWestLat(),
                request.getNorthEastLat(),
                request.getSouthWestLng(),
                request.getNorthEastLng()
        );

        return markers.stream()
                .map(MapMarkerResponseDto::from)
                .toList();

    }

    /**
     * 요청 DTO 기반으로 위치/마커 엔티티를 생성하고 저장한 뒤, 응답 DTO로 변환해 반환한다.
     */
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
                request.getPostId(),
                location,
                request.getTitle(),
                request.getSummary()
        );

        // 저장
        MapMarker saved = mapMarkerRepository.save(marker);

        // DTO로 변환해서 반환
        return MapMarkerResponseDto.from(saved);
    }

    /**
     * markerId로 마커를 조회하여 삭제한다.
     * - 조회 실패 시 404(MapMarker not found)를 반환한다.
     */
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
