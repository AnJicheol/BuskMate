package org.example.buskmate.band.dto.bandmember;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.example.buskmate.band.domain.BandMember;
import org.example.buskmate.band.domain.BandMemberRole;

import java.time.LocalDateTime;

/**
 * 밴드 멤버 목록 조회 시 사용되는 DTO입니다.
 *
 * <p>
 * 밴드에 소속된 멤버의 기본 정보(사용자 ID, 역할, 가입 일시)를
 * 리스트 형태로 클라이언트에 전달하기 위해 사용됩니다.
 * </p>
 *
 * @since 1.0.0
 */
@Getter
@Builder
@AllArgsConstructor
public class BandMemberListItemResponse {

    private String userId;
    private BandMemberRole role;
    private LocalDateTime joinedAt;

    /**
     * {@link BandMember} 엔티티를
     * {@link BandMemberListItemResponse} DTO로 변환합니다.
     *
     * @param member 밴드 멤버 엔티티
     * @return 변환된 밴드 멤버 목록 응답 DTO
     */
    public static BandMemberListItemResponse from(BandMember member) {
        return BandMemberListItemResponse.builder()
                .userId(member.getUserId())
                .role(member.getRole())
                .joinedAt(member.getJoinedAt())
                .build();
    }
}
