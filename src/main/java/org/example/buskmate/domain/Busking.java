package org.example.buskmate.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "buskings")
@Getter
@Setter
@NoArgsConstructor
public class Busking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false, name="busking_id", length=26)
    private String buskingId;

    // 공연 제목
    @Column(nullable = false)
    private String title;

    // 공연 시작/종료 시간
    @Column(nullable = false)
    private LocalDateTime buskingStart;
    private LocalDateTime buskingEnd;

    public Busking(
            String buskingId,
            String title,
            LocalDateTime buskingStart,
            LocalDateTime buskingEnd
    ) {
        this.buskingId = buskingId;
        this.title = title;
        this.buskingStart = buskingStart;
        this.buskingEnd = buskingEnd;
    }
}
