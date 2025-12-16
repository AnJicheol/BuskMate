package org.example.buskmate.band.dto.band;

import lombok.Builder;
import lombok.Getter;

/**
 * 밴드 목록 조회 시 사용되는 DTO입니다.
 *
 * <p>
 * 밴드 목록 화면에 필요한 최소한의 정보를 전달하기 위해 사용되며,
 * 여러 밴드 정보를 리스트 형태로 반환할 때 활용됩니다.
 * </p>
 *
 * @since 1.0.0
 */
@Getter
@Builder
public class BandListItemResponse {

    private String bandId;
    private String name;
    private String imageUrl;
}
