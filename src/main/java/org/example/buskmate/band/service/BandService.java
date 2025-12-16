package org.example.buskmate.band.service;

import org.example.buskmate.band.dto.band.*;

import java.util.List;

/**
 * 밴드 관련 비즈니스 로직을 정의하는 서비스 인터페이스입니다.
 *
 * <p>
 * 밴드 생성, 조회, 수정, 비활성화 등
 * 밴드 도메인의 핵심 기능을 제공합니다.
 * </p>
 *
 * @since 1.0.0
 */
public interface BandService {

    /**
     * 새로운 밴드를 생성합니다.
     */
    BandCreateResponse create(BandCreateRequest request, String leaderId);

    /**
     * 밴드 ID로 밴드 상세 정보를 조회합니다.
     */
    BandDetailResponse getByBandId(String bandId);

    /**
     * 활성 상태의 모든 밴드 목록을 조회합니다.
     */
    List<BandListItemResponse> getAllBands();

    /**
     * 밴드의 기본 정보를 수정합니다.
     */
    BandDetailResponse updateBand(String bandId, UpdateBandRequest req);

    /**
     * 밴드를 비활성화합니다.
     */
    void deactivate(String bandId);
}
