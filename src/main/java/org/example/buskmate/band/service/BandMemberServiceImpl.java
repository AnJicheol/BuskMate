package org.example.buskmate.band.service;

import lombok.RequiredArgsConstructor;
import org.example.buskmate.band.domain.Band;
import org.example.buskmate.band.domain.BandMember;
import org.example.buskmate.band.domain.BandMemberRole;
import org.example.buskmate.band.dto.BandMemberListItemResponse;
import org.example.buskmate.band.repository.BandMemberRepository;
import org.example.buskmate.band.repository.BandRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BandMemberServiceImpl implements BandMemberService {

    private final BandRepository bandRepository;
    private final BandMemberRepository bandMemberRepository;

    @Override
    public List<BandMemberListItemResponse> getMembers(String bandId) {

        Band band = bandRepository.findByBandIdAndStatusActive(bandId);
        if (band == null) {
            throw new IllegalArgumentException("해당 밴드가 존재하지 않습니다: " + bandId);
        }

        List<BandMember> members = bandMemberRepository.findAllByBand_BandId(bandId);

        return members.stream()
                .map(BandMemberListItemResponse::from)
                .toList();
    }

    @Override
    @Transactional
    public void inviteMember(String bandId, String userId) {
        addMemberInternal(bandId, userId);
    }

    private void addMemberInternal(String bandId, String userId) {
        Band band = bandRepository.findByBandIdAndStatusActive(bandId);
        if (band == null) {
            throw new IllegalArgumentException("해당 밴드가 존재하지 않습니다: " + bandId);
        }

        if (bandMemberRepository.existsByBand_BandIdAndUserId(bandId, userId)) {
            throw new IllegalStateException("이미 이 밴드의 멤버입니다.");
        }

        BandMember member = BandMember.join(band, userId, BandMemberRole.MEMBER);
        bandMemberRepository.save(member);
    }
}
