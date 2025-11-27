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
 * <p>
 * 이 클래스는 버스킹 밴드의 핵심 정보와 행위를 캡슐화하며, JPA를 통해 데이터베이스와 매핑됩니다.
 * 밴드의 수명 주기(생성, 수정, 비활성화)를 관리하고, 도메인 규칙을 적용합니다.
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
@Table(name = "bands",
        indexes = {
                @Index(name = "idx_band_band_id", columnList = "band_id", unique = true),
                @Index(name = "idx_band_leader_id", columnList = "leader_id")
        })
@Entity
public class Band {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "band_id", nullable = false, length = 26, unique = true, updatable = false)
    private String bandId;

    @Column(nullable = false, length = 60)
    private String name;

    @Column(name = "leader_id", nullable = false, length = 64)
    private String leaderId;

    @Column(name = "image_url", length = 512)
    private String imageUrl;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 16)
    private BandStatus status;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    // ===== 생성 =====

    @Builder
    private Band(String bandId, String name, String leaderId, String imageUrl) {
        this.bandId = bandId;                 // null이면 @PrePersist에서 채움
        this.name = name;
        this.leaderId = leaderId;
        this.imageUrl = imageUrl;
        this.status = BandStatus.ACTIVE;
    }

    /**
     * 새로운 밴드 인스턴스를 생성하는 정적 팩토리 메서드입니다.
     *
     * @param name 밴드 이름 (필수)
     * @param leaderId 밴드장의 사용자 식별자 (필수)
     * @param imageUrl 밴드 대표 이미지 URL (선택)
     * @return 새로운 {@code Band} 인스턴스
     * @throws IllegalArgumentException 필수 파라미터가 null이거나 비어있는 경우
     */
    public static Band create(String name, String leaderId, String imageUrl) {
        return Band.builder()
                .name(name)
                .leaderId(leaderId)
                .imageUrl(imageUrl)
                .build();
    }

    // ===== 도메인 로직 =====

    /**
     * 밴드의 기본 정보를 업데이트합니다.
     *
     * @param name 새로 설정할 밴드 이름. null이거나 빈 문자열인 경우 기존 값 유지
     * @param imageUrl 새로 설정할 이미지 URL. null 허용 (이미지 제거)
     */
    public void updateInfo(String name, String imageUrl) {
        if (name != null && !name.isBlank()) {
            this.name = name.trim();
        }
        this.imageUrl = imageUrl; // null 허용(이미지 제거)
    }

    /**
     * 밴드를 비활성화합니다.
     * <p>
     * 이 메서드는 밴드의 상태를 {@link BandStatus#INACTIVE}로 변경하여
     * 소프트 삭제를 수행합니다. 이 작업은 되돌릴 수 없으며,
     * 이후 해당 밴드는 조회에서 제외되어야 합니다.
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
