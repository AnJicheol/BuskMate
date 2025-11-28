package org.example.buskmate.band.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(
        name = "band_members",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_band_member_band_user",
                        columnNames = {"band_id", "user_id"}
                )
        }
)
@Entity
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

    private BandMember(Band band, String userId, BandMemberRole role) {
        this.band = band;
        this.userId = userId;
        this.role = role;
    }

    public static BandMember join(Band band, String userId, BandMemberRole role) {
        return new BandMember(band, userId, role);
    }
}
