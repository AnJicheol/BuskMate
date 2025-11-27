package org.example.buskmate.band.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BandDetailResponse {

    private String bandId;
    private String name;
    private String leaderId;
    private String imageUrl;
    private String createdAt;
}
