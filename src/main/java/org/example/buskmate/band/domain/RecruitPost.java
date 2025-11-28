package org.example.buskmate.band.domain;


import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
/**
 * 밴드 모집 게시글 엔티티를 나타냅니다.
 * <p>
 * 이 엔티티는 데이터베이스 테이블 "recruit_posts"에 매핑되며,
 * 게시글의 ID, 밴드 ID, 제목, 내용, 상태, 생성 날짜와 같은 정보를 제공합니다.
 * </p>
 *
 * <p>
 * <strong>특이사항:</strong>
 * <ul>
 *   <li>엔티티 필드는 camelCase를 사용하며, 데이터베이스 컬럼은 snake_case를 따릅니다.</li>
 *   <li>게시글 정보를 변경하거나, 닫거나 삭제하는 유틸리티 메서드를 제공합니다.</li>
 * </ul>
 * </p>
 *
 * @author [작성자 이름]
 * @version 1.0
 * @since 2025-11-25
 */


@Getter
@NoArgsConstructor
@Entity@Table(name = "recruit_posts",
        indexes = {@Index(name = "idx_recruitpost_post_id", columnList = "post_id", unique = true)})
public class RecruitPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="post_id", nullable = false, unique=true)
    private String postId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "band_id", nullable = false)
    private Band band;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, length = 2000)
    private String content;

    @Enumerated(EnumType.STRING)
    private RecruitPostStatus status;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Builder
    private RecruitPost(String postId, Band band, String title, String content) {
        this.postId = postId;
        this.band = band;
        this.title = title;
        this.content = content;
        this.status = RecruitPostStatus.OPEN;
    }

    public void updateInfo(String newTitle, String newContent) {
        this.title = newTitle;
        this.content = newContent;
    }

    public void close() { this.status = RecruitPostStatus.CLOSED; }

    public void delete(){ this.status=RecruitPostStatus.DELETED;}
}
