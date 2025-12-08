package org.example.buskmate.busking.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "busking")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Busking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "busking_id", nullable = false, unique = true)
    private String buskingId;

    @Column(name = "band_id")
    private String bandId;

    @Column(name = "start_at", nullable = false)
    private LocalDateTime startAt;

    @Column(name = "end_at", nullable = false)
    private LocalDateTime endAt;

    @Column(nullable = false)
    private String title;

    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BuskingStatus status;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Builder
    private Busking(String buskingId,
                    LocalDateTime startAt,
                    LocalDateTime endAt,
                    String title,
                    String description,
                    BuskingStatus status) {

        if (startAt.isAfter(endAt)) {
            throw new IllegalArgumentException("startAt > endAt");
        }

        this.buskingId = buskingId;
        this.startAt = startAt;
        this.endAt = endAt;
        this.title = title;
        this.description = description;
        this.status = status != null ? status : BuskingStatus.OPEN;
    }

    @PrePersist
    void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = this.createdAt;
    }

    @PreUpdate
    void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public void changeBasicInfo(String title, String description,
                                LocalDateTime startAt, LocalDateTime endAt) {
        if (startAt != null && endAt != null && startAt.isAfter(endAt)) {
            throw new IllegalArgumentException("startAt > endAt");
        }
        if (title != null) this.title = title;
        if (description != null) this.description = description;
        if (startAt != null) this.startAt = startAt;
        if (endAt != null) this.endAt = endAt;
    }

    public void reserveBy(String bandId) {
        if (this.bandId != null) {
            throw new IllegalStateException("이미 예약된 버스킹입니다.");
        }
        this.bandId = bandId;
        this.status = BuskingStatus.RESERVED;
    }

    public void cancel() {
        this.status = BuskingStatus.CANCELED;
    }

    public void finish() {
        this.status = BuskingStatus.FINISHED;
    }
}
