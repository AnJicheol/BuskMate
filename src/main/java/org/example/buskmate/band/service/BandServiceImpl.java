package org.example.buskmate.band.service;

import lombok.RequiredArgsConstructor;
import org.example.buskmate.band.domain.Band;
import org.example.buskmate.band.dto.BandDetailResponse;
import org.example.buskmate.band.dto.BandListItemResponse;
import org.example.buskmate.band.repository.BandRepository;
import org.example.buskmate.band.dto.BandCreateRequest;
import org.example.buskmate.band.dto.BandCreateResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BandServiceImpl implements BandService {

    private final BandRepository bandRepository;

    @Override
    @Transactional
    public BandCreateResponse create(BandCreateRequest request) {

        String leaderId = request.getLeaderId();

        // 유효성 검증
        if (request.getName() == null || request.getName().isBlank()) {
            throw new IllegalArgumentException("밴드 이름을 작성해 주세요.");
        }

        // 엔티티 생성
        Band band = Band.create(
                request.getName(),
                leaderId,
                request.getImageUrl()
        );

        // 저장
        bandRepository.save(band);

        // 응답 생성
        return BandCreateResponse.builder()
                .bandId(band.getBandId())
                .name(band.getName())
                .leaderId(band.getLeaderId())
                .imageUrl(band.getImageUrl())
                .createdAt(band.getCreatedAt().toString())
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public BandDetailResponse getByBandId(String bandId) {

        Band band = bandRepository.findByBandIdAndStatusActive(bandId);

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

    @Override
    @Transactional(readOnly = true)
    public List<BandListItemResponse> getAllBands() {

        return bandRepository.findAllByStatusActive()
                .stream()
                .map(band -> BandListItemResponse.builder()
                        .bandId(band.getBandId())
                        .name(band.getName())
                        .imageUrl(band.getImageUrl())
                        .build())
                .toList();
    }
}
