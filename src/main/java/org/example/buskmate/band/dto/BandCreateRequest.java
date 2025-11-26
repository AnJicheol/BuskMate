package org.example.buskmate.band.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BandCreateRequest {

    private String name;
    private String imageUrl; // 선택 필드
    private String leaderId;
}
