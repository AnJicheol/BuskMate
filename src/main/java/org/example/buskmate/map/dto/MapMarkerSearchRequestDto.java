package org.example.buskmate.map.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


/**
 * 지도 마커 조회 요청 DTO
 * - 지도 bounds(남서/북동 좌표)와 타입 필터를 전달하기 위한 DTO다.
 */
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
