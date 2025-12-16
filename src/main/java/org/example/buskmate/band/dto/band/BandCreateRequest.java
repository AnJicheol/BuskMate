package org.example.buskmate.band.dto.band;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 밴드 생성 요청을 위한 DTO입니다.
 *
 * <p>
 * 클라이언트로부터 전달받은 밴드 생성 정보를 담아
 * 밴드 생성 API에서 사용됩니다.
 * </p>
 *
 * @since 1.0.0
 */
@Getter
@NoArgsConstructor
public class BandCreateRequest {

    @NotBlank(message = "밴드 이름을 작성해 주세요.")
    private String name;

    private String imageUrl;
}
