package org.example.buskmate.band.service;

import org.example.buskmate.band.domain.Band;
import org.example.buskmate.band.dto.bandmember.BandMemberListItemResponse;

import java.util.List;

/**
 * 밴드 멤버 관련 비즈니스 로직을 정의하는 서비스 인터페이스입니다.
 *
 * <p>
 * 밴드 멤버 조회, 초대, 초대 수락/거절,
 * 멤버 등록 및 추방과 같은 밴드 멤버십 관리 기능을 제공합니다.
 * </p>
 *
 * @since 1.0.0
 */
public interface BandMemberService {

    /**
     * 특정 밴드에 소속된 멤버 목록을 조회합니다.
     */
    List<BandMemberListItemResponse> getMembers(String bandId);

    /**
     * 밴드 리더가 특정 사용자를 밴드에 초대합니다.
     */
    void inviteMember(String bandId, String leaderId, String targetUserId);

    /**
     * 밴드 초대를 수락합니다.
     */
    void acceptInvitation(String bandId, String userId);

    /**
     * 밴드 초대를 거절합니다.
     */
    void rejectInvitation(String bandId, String userId);

    /**
     * 초대 또는 모집 승인 후,
     * 사용자를 밴드의 활성 멤버로 등록합니다.
     */
    void addMemberAccepted(Band band, String userId);

    /**
     * 밴드 리더가 활성 상태의 멤버를 밴드에서 추방합니다.
     */
    void kickMember(String bandId, String leaderId, String targetUserId);
}
