package org.example.buskmate.band.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

/**
 * 버스킹 밴드를 나타내는 도메인 엔티티입니다.
 *
 * <p>
 * 버스킹 팀(밴드)의 기본 정보와 상태를 관리하며,
 * 밴드 생성, 정보 수정, 비활성화와 같은 핵심 도메인 행위를 포함합니다.
 * 본 엔티티는 JPA를 통해 데이터베이스와 매핑됩니다.
 * </p>
 *
 * <h3>주요 특징</h3>
 * <ul>
 *   <li><b>내부 식별자</b>: id (Long, 자동 증가)</li>
 *   <li><b>외부 식별자</b>: bandId (ULID 형식의 고유 문자열)</li>
 *   <li><b>상태 관리</b>: ACTIVE(활성) / INACTIVE(비활성, 소프트 삭제)</li>
 *   <li><b>자동 생성 값</b>: 생성일시(createdAt)는 자동으로 설정됨</li>
 * </ul>
 *
 * @see BandStatus
 * @since 1.0.0
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(
        name = "bands",
        indexes = {
                @Index(name = "idx_band_band_id", columnList = "band_id", unique = true),
                @Index(name = "idx_band_leader_id", columnList = "leader_id")
        }
)
public class Band {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "band_id", nullable = false, length = 26, unique = true, updatable = false)
    private String bandId;

    @Column(nullable = false, length = 60)
    private String name;

    @Column(name = "leader_id", nullable = false, length = 64, updatable = false)
    private String leaderId;

    @Column(name = "image_url", length = 512)
    private String imageUrl;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 16)
    private BandStatus status;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Builder
    private Band(String bandId, String name, String leaderId, String imageUrl) {
        this.bandId = bandId;
        this.name = name;
        this.leaderId = leaderId;
        this.imageUrl = imageUrl;
        this.status = BandStatus.ACTIVE;
    }

    /**
     * 새로운 밴드 인스턴스를 생성합니다.
     */
    public static Band create(String name, String leaderId, String imageUrl) {
        return Band.builder()
                .name(name)
                .leaderId(leaderId)
                .imageUrl(imageUrl)
                .build();
    }

    /**
     * 밴드의 기본 정보를 수정합니다.
     */
    public void updateInfo(String name, String imageUrl) {
        if (name != null && !name.isBlank()) {
            this.name = name.trim();
        }
        this.imageUrl = imageUrl;
    }

    /**
     * 밴드를 비활성화합니다.
     */
    public void deactivate() {
        this.status = BandStatus.INACTIVE;
    }

    @PrePersist
    private void fillBandIdIfNull() {
        if (this.bandId == null || this.bandId.isBlank()) {
            this.bandId = com.github.f4b6a3.ulid.UlidCreator.getUlid().toString();
        }
    }
}
