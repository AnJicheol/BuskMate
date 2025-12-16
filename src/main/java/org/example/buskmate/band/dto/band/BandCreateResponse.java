package org.example.buskmate.band.dto.band;

import lombok.Builder;
import lombok.Getter;

/**
 * 밴드 생성 결과를 반환하기 위한 DTO입니다.
 *
 * <p>
 * 밴드 생성 API 호출 성공 시,
 * 생성된 밴드의 기본 정보를 클라이언트에 전달하는 용도로 사용됩니다.
 * </p>
 *
 * @since 1.0.0
 */
@Getter
@Builder
public class BandCreateResponse {

    private String bandId;
    private String name;
    private String leaderId;
    private String imageUrl;
    private String createdAt;
}
