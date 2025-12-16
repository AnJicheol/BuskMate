package org.example.buskmate.map.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.buskmate.map.domain.MapMarker;
import org.example.buskmate.map.domain.MarkerType;

/**
 * 지도 마커 응답 DTO
 * - 엔티티(MapMarker)를 클라이언트 응답 형태로 변환한다.
 */
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MapMarkerResponseDto{

    private Long id;
    private MarkerType markerType;
    private double lat;
    private double lng;
    private String title;
    private String summary;

    public static MapMarkerResponseDto from(MapMarker marker) {
        return MapMarkerResponseDto.builder()
                .id(marker.getId())
                .markerType(marker.getMarkerType())
                .lat(marker.getLocation().getLat())
                .lng(marker.getLocation().getLng())
                .title(marker.getTitle())
                .summary(marker.getSummary())
                .build();
    }
}
