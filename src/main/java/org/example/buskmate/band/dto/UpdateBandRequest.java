package org.example.buskmate.band.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateBandRequest {
    private String name;
    private String imageUrl;
}
