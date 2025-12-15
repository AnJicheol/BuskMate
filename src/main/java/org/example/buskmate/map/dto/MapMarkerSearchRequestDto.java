package org.example.buskmate.map.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MapMarkerSearchRequestDto {

    private double southWestLat;
    private double southWestLng;
    private double northEastLat;
    private double northEastLng;


}
