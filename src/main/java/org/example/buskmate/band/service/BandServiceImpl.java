package org.example.buskmate.band.service;

import lombok.RequiredArgsConstructor;
import org.example.buskmate.band.domain.Band;
import org.example.buskmate.band.domain.BandStatus;
import org.example.buskmate.band.dto.band.*;
import org.example.buskmate.band.repository.BandRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 밴드 관련 비즈니스 로직을 구현한 서비스 클래스입니다.
 *
 * <p>
 * 밴드 생성, 조회, 목록 조회, 정보 수정, 비활성화 등
 * 밴드 도메인의 핵심 기능을 처리합니다.
 * </p>
 *
 * @since 1.0.0
 */
@Service
@RequiredArgsConstructor
public class BandServiceImpl implements BandService {

    private final BandRepository bandRepository;

    /**
     * 새로운 밴드를 생성합니다.
     */
    @Override
    @Transactional
    public BandCreateResponse create(BandCreateRequest request, String leaderId) {

        Band band = Band.create(
                request.getName(),
                leaderId,
                request.getImageUrl()
        );

        bandRepository.save(band);

        return BandCreateResponse.builder()
                .bandId(band.getBandId())
                .name(band.getName())
                .leaderId(band.getLeaderId())
                .imageUrl(band.getImageUrl())
                .createdAt(band.getCreatedAt().toString())
                .build();
    }

    /**
     * 밴드 ID로 활성 상태의 밴드 상세 정보를 조회합니다.
     */
    @Override
    @Transactional(readOnly = true)
    public BandDetailResponse getByBandId(String bandId) {

        Band band = bandRepository.findByBandIdAndStatus(bandId, BandStatus.ACTIVE);
        if (band == null) {
            throw new IllegalArgumentException("해당 밴드가 존재하지 않습니다: " + bandId);
        }

        return BandDetailResponse.builder()
                .bandId(band.getBandId())
                .name(band.getName())
                .leaderId(band.getLeaderId())
                .imageUrl(band.getImageUrl())
                .createdAt(band.getCreatedAt().toString())
                .build();
    }

    /**
     * 활성 상태의 모든 밴드 목록을 조회합니다.
     */
    @Override
    @Transactional(readOnly = true)
    public List<BandListItemResponse> getAllBands() {

        return bandRepository.findAllByStatus(BandStatus.ACTIVE)
                .stream()
                .map(band -> BandListItemResponse.builder()
                        .bandId(band.getBandId())
                        .name(band.getName())
                        .imageUrl(band.getImageUrl())
                        .build())
                .toList();
    }

    /**
     * 밴드의 기본 정보를 수정합니다.
     */
    @Override
    @Transactional
    public BandDetailResponse updateBand(String bandId, UpdateBandRequest req) {

        Band band = bandRepository.findByBandIdAndStatus(bandId, BandStatus.ACTIVE);
        if (band == null) {
            throw new IllegalArgumentException("해당 밴드가 존재하지 않습니다: " + bandId);
        }

        band.updateInfo(req.getName(), req.getImageUrl());

        return BandDetailResponse.builder()
                .bandId(band.getBandId())
                .name(band.getName())
                .leaderId(band.getLeaderId())
                .imageUrl(band.getImageUrl())
                .createdAt(band.getCreatedAt().toString())
                .build();
    }

    /**
     * 밴드를 비활성화합니다.
     */
    @Override
    @Transactional
    public void deactivate(String bandId) {

        Band band = bandRepository.findByBandIdAndStatus(bandId, BandStatus.ACTIVE);
        if (band == null) {
            throw new IllegalArgumentException("해당 밴드가 존재하지 않습니다: " + bandId);
        }

        band.deactivate();
    }
}
