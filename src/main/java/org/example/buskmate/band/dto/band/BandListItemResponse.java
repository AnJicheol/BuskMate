package org.example.buskmate.band.dto.band;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BandListItemResponse {

    private String bandId;
    private String name;
    private String imageUrl;
}
