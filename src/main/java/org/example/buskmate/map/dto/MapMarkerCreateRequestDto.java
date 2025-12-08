package org.example.buskmate.map.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.buskmate.map.domain.MarkerType;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MapMarkerCreateRequestDto {

    @NotNull
    private MarkerType markerType;

    @NotNull
    private Double lat;

    @NotNull
    private Double lng;

    @NotBlank
    private String title;

    private String summary;
}
