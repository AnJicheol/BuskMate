package org.example.buskmate.band.service;

import lombok.RequiredArgsConstructor;
import org.example.buskmate.band.domain.*;
import org.example.buskmate.band.dto.bandmember.BandMemberListItemResponse;
import org.example.buskmate.band.repository.BandMemberRepository;
import org.example.buskmate.band.repository.BandRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 밴드 멤버 관련 비즈니스 로직을 구현한 서비스 클래스입니다.
 *
 * <p>
 * 밴드 멤버 조회, 초대, 초대 수락/거절, 멤버 등록 및 추방 등
 * 밴드 멤버십 관리 전반의 비즈니스 규칙을 처리합니다.
 * </p>
 *
 * @since 1.0.0
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BandMemberServiceImpl implements BandMemberService {

    private final BandRepository bandRepository;
    private final BandMemberRepository bandMemberRepository;

    /**
     * 특정 밴드에 소속된 활성 상태의 멤버 목록을 조회합니다.
     */
    @Override
    public List<BandMemberListItemResponse> getMembers(String bandId) {

        Band band = bandRepository.findByBandIdAndStatus(bandId, BandStatus.ACTIVE);
        if (band == null) {
            throw new IllegalArgumentException("해당 밴드가 존재하지 않습니다: " + bandId);
        }

        List<BandMember> members =
                bandMemberRepository.findAllByBand_BandIdAndStatus(
                        bandId,
                        BandMemberStatus.ACTIVE
                );

        return members.stream()
                .map(BandMemberListItemResponse::from)
                .toList();
    }

    /**
     * 밴드 리더가 특정 사용자를 밴드에 초대합니다.
     */
    @Override
    @Transactional
    public void inviteMember(String bandId, String leaderId, String targetUserId) {

        Band band = bandRepository.findByBandIdAndStatus(bandId, BandStatus.ACTIVE);
        if (band == null) {
            throw new IllegalArgumentException("해당 밴드가 존재하지 않습니다: " + bandId);
        }

        if (!band.getLeaderId().equals(leaderId)) {
            throw new IllegalStateException("밴드 리더만 멤버를 초대할 수 있습니다.");
        }

        if (bandMemberRepository.existsByBand_BandIdAndUserIdAndStatus(
                bandId,
                targetUserId,
                BandMemberStatus.ACTIVE
        )) {
            throw new IllegalStateException("이미 이 밴드의 멤버입니다.");
        }

        if (bandMemberRepository.existsByBand_BandIdAndUserIdAndStatus(
                bandId,
                targetUserId,
                BandMemberStatus.INVITED
        )) {
            throw new IllegalStateException("이미 초대 대기 중인 멤버입니다.");
        }

        BandMember member =
                BandMember.invited(band, targetUserId, BandMemberRole.MEMBER);

        bandMemberRepository.save(member);
    }

    /**
     * 밴드 초대를 수락합니다.
     */
    @Override
    @Transactional
    public void acceptInvitation(String bandId, String userId) {

        BandMember member = bandMemberRepository
                .findByBand_BandIdAndUserId(bandId, userId)
                .orElseThrow(() ->
                        new IllegalArgumentException("멤버 정보를 찾을 수 없습니다.")
                );

        member.accept();
    }

    /**
     * 밴드 초대를 거절합니다.
     */
    @Override
    @Transactional
    public void rejectInvitation(String bandId, String userId) {

        BandMember member = bandMemberRepository
                .findByBand_BandIdAndUserId(bandId, userId)
                .orElseThrow(() ->
                        new IllegalArgumentException("멤버 정보를 찾을 수 없습니다.")
                );

        member.reject();
    }

    /**
     * 초대 수락 또는 모집 승인 후,
     * 사용자를 밴드의 활성 멤버로 등록합니다.
     */
    @Override
    @Transactional
    public void addMemberAccepted(Band band, String userId) {

        boolean exists = bandMemberRepository
                .existsByBand_BandIdAndUserId(band.getBandId(), userId);

        if (exists) {
            return;
        }

        BandMember bandMember =
                BandMember.active(band, userId, BandMemberRole.MEMBER);

        bandMemberRepository.save(bandMember);
    }

    /**
     * 밴드 리더가 특정 멤버를 밴드에서 추방합니다.
     */
    @Override
    @Transactional
    public void kickMember(String bandId, String leaderId, String targetUserId) {

        Band band = bandRepository.findByBandIdAndStatus(bandId, BandStatus.ACTIVE);
        if (band == null) {
            throw new IllegalArgumentException("해당 밴드가 존재하지 않습니다: " + bandId);
        }

        if (!band.getLeaderId().equals(leaderId)) {
            throw new IllegalStateException("밴드 리더만 멤버를 추방할 수 있습니다.");
        }

        if (band.getLeaderId().equals(targetUserId)) {
            throw new IllegalStateException("밴드 리더는 추방할 수 없습니다.");
        }

        BandMember member = bandMemberRepository
                .findByBand_BandIdAndUserId(bandId, targetUserId)
                .orElseThrow(() ->
                        new IllegalArgumentException("해당 유저는 이 밴드의 멤버가 아닙니다.")
                );

        member.kick();
    }
}
