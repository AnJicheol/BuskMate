package org.example.buskmate.map.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 지도 마커 생성 요청 DTO
 * - 마커 등록 요청에서 필요한 입력값을 담는다.
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MapMarkerCreateRequestDto {

    @NotNull
    private String postId;

    @NotNull
    private Double lat;

    @NotNull
    private Double lng;

    @NotBlank
    private String title;

    private String summary;
}
