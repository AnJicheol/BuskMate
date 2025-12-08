package org.example.buskmate.band.dto.bandmember;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.example.buskmate.band.domain.BandMember;
import org.example.buskmate.band.domain.BandMemberRole;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class BandMemberListItemResponse {

    private String userId;
    private BandMemberRole role;
    private LocalDateTime joinedAt;

    public static BandMemberListItemResponse from(BandMember member) {
        return BandMemberListItemResponse.builder()
                .userId(member.getUserId())
                .role(member.getRole())
                .joinedAt(member.getJoinedAt())
                .build();
    }
}
