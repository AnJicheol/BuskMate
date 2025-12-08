package org.example.buskmate.band.dto.band;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BandCreateResponse {

    private String bandId;
    private String name;
    private String leaderId;
    private String imageUrl;
    private String createdAt;
}
