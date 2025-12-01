package org.example.buskmate.band.service;

import lombok.RequiredArgsConstructor;
import org.example.buskmate.band.domain.Band;
import org.example.buskmate.band.domain.BandMember;
import org.example.buskmate.band.domain.BandMemberRole;
import org.example.buskmate.band.domain.BandMemberStatus;
import org.example.buskmate.band.dto.bandmember.BandMemberListItemResponse;
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

        List<BandMember> members =
                bandMemberRepository.findAllByBand_BandIdAndStatus(bandId, BandMemberStatus.ACTIVE);

        return members.stream()
                .map(BandMemberListItemResponse::from)
                .toList();
    }


    @Override
    @Transactional
    public void inviteMember(String bandId, String leaderId, String targetUserId) {
        Band band = bandRepository.findByBandIdAndStatusActive(bandId);
        if (band == null) {
            throw new IllegalArgumentException("해당 밴드가 존재하지 않습니다: " + bandId);
        }

        // 리더인지 확인
        if (!band.getLeaderId().equals(leaderId)) {
            throw new IllegalStateException("밴드 리더만 멤버를 초대할 수 있습니다.");
        }

        // 이미 ACTIVE 멤버인지 확인
        if (bandMemberRepository.existsByBand_BandIdAndUserIdAndStatus(bandId, targetUserId, BandMemberStatus.ACTIVE)) {
            throw new IllegalStateException("이미 이 밴드의 멤버입니다.");
        }

        // 이미 초대 상태인 경우 처리
        if (bandMemberRepository.existsByBand_BandIdAndUserIdAndStatus(bandId, targetUserId, BandMemberStatus.INVITED)) {
            throw new IllegalStateException("이미 초대 대기 중인 멤버입니다.");
        }

        BandMember member = BandMember.invited(band, targetUserId, BandMemberRole.MEMBER);
        bandMemberRepository.save(member);
    }

    @Override
    @Transactional
    public void acceptInvitation(String bandId, String userId) {
        BandMember member = bandMemberRepository
                .findByBand_BandIdAndUserId(bandId, userId)
                .orElseThrow(() -> new IllegalArgumentException("멤버 정보를 찾을 수 없습니다."));

        member.accept();
    }

    @Override
    @Transactional
    public void rejectInvitation(String bandId, String userId) {
        BandMember member = bandMemberRepository
                .findByBand_BandIdAndUserId(bandId, userId)
                .orElseThrow(() -> new IllegalArgumentException("멤버 정보를 찾을 수 없습니다."));

        member.reject();
    }

}
