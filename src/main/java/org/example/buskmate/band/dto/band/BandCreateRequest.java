package org.example.buskmate.band.dto.band;

import lombok.Getter;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class BandCreateRequest {

    @NotBlank(message = "밴드 이름을 작성해 주세요.")
    private String name;

    private String imageUrl; // 선택 필드
    private String leaderId;

}
