package org.example.buskmate.band.dto.band;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 밴드 정보 수정을 위한 요청 DTO입니다.
 *
 * <p>
 * 밴드의 이름 및 대표 이미지 정보를 수정하기 위한 요청 데이터를 담으며,
 * 수정 API에서 사용됩니다.
 * </p>
 *
 * @since 1.0.0
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateBandRequest {

    private String name;
    private String imageUrl;
}
