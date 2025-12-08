package org.example.buskmate.map.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.buskmate.map.domain.MapMarker;
import org.example.buskmate.map.domain.MarkerType;

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
