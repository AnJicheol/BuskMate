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

    /**
     * 데이터베이스 내부에서 사용되는 고유 식별자입니다.
     * <p>
     * 자동 증가(Auto Increment) 방식으로 생성되며, 비즈니스 로직에서는 사용하지 않는 것이 좋습니다.
     * 대신 {@code bandId}를 사용하세요.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 외부에 노출되는 고유 식별자입니다.
     * <p>
     * ULID(Universally Unique Lexicographically Sortable Identifier) 형식으로,
     * UUID보다 효율적인 인덱싱이 가능합니다. 이 값은 생성 후 변경할 수 없습니다.
     *
     * @see <a href="https://github.com/ulid/spec">ULID 스펙</a>
     */
    @Column(name = "band_id", nullable = false, length = 26, unique = true, updatable = false)
    private String bandId;

    /**
     * 밴드의 이름입니다.
     * <p>
     * 최대 60자까지 입력 가능하며, 필수 입력 항목입니다.
     * {@link #updateInfo(String, String)} 메서드를 통해 수정할 수 있습니다.
     */
    @Column(nullable = false, length = 60)
    private String name;

    /**
     * 밴드장(리더)의 사용자 식별자입니다.
     * <p>
     * 사용자 도메인의 외부 식별자를 참조합니다.
     * 이 값은 밴드 생성 시 설정되며, 변경이 필요한 경우 별도의 도메인 이벤트를 통해 처리해야 합니다.
     */
    @Column(name = "leader_id", nullable = false, length = 64)
    private String leaderId;

    /**
     * 밴드의 대표 이미지 URL입니다.
     * <p>
     * 선택적 필드이며, 이미지가 없는 경우 null일 수 있습니다.
     * {@link #updateInfo(String, String)} 메서드를 통해 수정할 수 있습니다.
     */
    @Column(name = "image_url", length = 512)
    private String imageUrl;

    /**
     * 밴드의 현재 상태를 나타냅니다.
     * <p>
     * 기본값은 ACTIVE이며, {@link #deactivate()} 메서드를 호출하여 INACTIVE로 변경할 수 있습니다.
     * INACTIVE 상태는 소프트 삭제를 의미합니다.
     *
     * @see BandStatus
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 16)
    private BandStatus status;

    /**
     * 밴드가 생성된 일시입니다.
     * <p>
     * 엔티티가 처음 영속화될 때 자동으로 현재 시각이 설정되며,
     * 이후로는 변경할 수 없습니다.
     *
     * @see CreationTimestamp
     */
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

    /**
     * 엔티티가 영속화되기 전에 호출되는 콜백 메서드입니다.
     * <p>
     * {@code bandId}가 설정되어 있지 않은 경우, 자동으로 ULID를 생성하여 설정합니다.
     * 이 메서드는 JPA의 {@code @PrePersist} 어노테이션에 의해 자동으로 호출됩니다.
     *
     * @see PrePersist
     */
    @PrePersist
    private void fillBandIdIfNull() {
        if (this.bandId == null || this.bandId.isBlank()) {
            // com.github.f4b6a3.ulid:ulid-creator 사용 (팀 공통)
            this.bandId = com.github.f4b6a3.ulid.UlidCreator.getUlid().toString();
        }
    }
}
