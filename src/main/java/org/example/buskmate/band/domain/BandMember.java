package org.example.buskmate.band.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

/**
 * 밴드 멤버를 나타내는 도메인 엔티티입니다.
 *
 * <p>
 * 특정 밴드에 소속된 사용자의 참여 상태와 역할을 관리합니다.
 * 밴드 초대, 가입 승인/거절, 활동 중인 멤버의 추방 등
 * 밴드 멤버십과 관련된 핵심 도메인 규칙을 포함합니다.
 * </p>
 *
 * <h3>주요 특징</h3>
 * <ul>
 *   <li>하나의 밴드(Band)에 소속된 사용자 정보를 표현</li>
 *   <li>밴드 내 역할(Role)과 멤버 상태(Status)를 함께 관리</li>
 *   <li>초대 → 활성 → 추방/거절 흐름의 상태 전이 규칙 포함</li>
 * </ul>
 *
 * @see BandMemberRole
 * @see BandMemberStatus
 * @since 1.0.0
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(
        name = "band_members",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_band_member_band_user",
                        columnNames = {"band_id", "user_id"}
                )
        }
)
public class BandMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "band_id", nullable = false)
    private Band band;

    @Column(name = "user_id", nullable = false, length = 64)
    private String userId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 16)
    private BandMemberRole role;

    @CreationTimestamp
    @Column(name = "joined_at", nullable = false, updatable = false)
    private LocalDateTime joinedAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 16)
    private BandMemberStatus status;

    private BandMember(Band band, String userId, BandMemberRole role, BandMemberStatus status) {
        this.band = band;
        this.userId = userId;
        this.role = role;
        this.status = status;
    }

    /**
     * 밴드에 초대된 멤버를 생성합니다.
     */
    public static BandMember invited(Band band, String userId, BandMemberRole role) {
        return new BandMember(band, userId, role, BandMemberStatus.INVITED);
    }

    /**
     * 초대된 멤버를 활성 상태로 전환합니다.
     */
    public void accept() {
        if (this.status != BandMemberStatus.INVITED) {
            throw new IllegalStateException("초대 상태가 아닙니다.");
        }
        this.status = BandMemberStatus.ACTIVE;
    }

    /**
     * 밴드 초대를 거절합니다.
     */
    public void reject() {
        if (this.status != BandMemberStatus.INVITED) {
            throw new IllegalStateException("초대 상태가 아닙니다.");
        }
        this.status = BandMemberStatus.REJECTED;
    }

    /**
     * 활성 상태인 밴드 멤버를 생성합니다.
     */
    public static BandMember active(Band band, String userId, BandMemberRole role) {
        return new BandMember(band, userId, role, BandMemberStatus.ACTIVE);
    }

    /**
     * 활성 상태의 멤버를 밴드에서 추방합니다.
     */
    public void kick() {
        if (this.status != BandMemberStatus.ACTIVE) {
            throw new IllegalStateException("활동 중인 멤버만 추방할 수 있습니다.");
        }
        this.status = BandMemberStatus.KICKED;
    }
}
