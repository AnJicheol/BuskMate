package org.example.buskmate.map.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.buskmate.map.domain.MapMarker;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MapMarkerResponseDto{

    private Long id;
    private String postId;
    private double lat;
    private double lng;
    private String title;
    private String summary;

    public static MapMarkerResponseDto from(MapMarker marker) {
        return MapMarkerResponseDto.builder()
                .id(marker.getId())
                .postId(marker.getPostId())
                .lat(marker.getLocation().getLat())
                .lng(marker.getLocation().getLng())
                .title(marker.getTitle())
                .summary(marker.getSummary())
                .build();
    }
}
