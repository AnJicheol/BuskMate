package org.example.buskmate.band.domain;


import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
/**
 * 밴드 팀원 모집을 위한 모집 글(Recruit Post)을 나타내는 도메인 엔티티입니다.
 *
 * <p>
 * {@code RecruitPost}는 특정 밴드({@link Band})가 새로운 멤버를 모집하기 위해
 * 작성하는 게시글을 표현하며, 제목, 내용, 상태 및 생성 시점 정보를 포함합니다.
 * </p>
 *
 * <h3>주요 특징</h3>
 * <ul>
 *   <li>모집 글은 밴드 단위로 관리됩니다.</li>
 *   <li>모집 상태는 {@link RecruitPostStatus}를 통해 관리됩니다.</li>
 *   <li>실제 물리 삭제가 아닌 상태 기반(OPEN / CLOSED / DELETED)으로 관리됩니다.</li>
 * </ul>
 *
 * <p>
 * 모집 글은 생성 이후 수정, 마감, 삭제(비활성화)가 가능하며,
 * 이러한 상태 변경 로직은 엔티티 내부 메서드를 통해 수행됩니다.
 * </p>
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
    /**
     * 모집 글의 제목과 내용을 수정합니다.
     *
     * <p>
     * 해당 메서드는 모집 글 작성자(밴드 리더)에 의해 호출되며,
     * 제목과 본문 내용을 동시에 변경합니다.
     * </p>
     *
     * @param newTitle   수정할 제목
     * @param newContent 수정할 내용
     */
    public void updateInfo(String newTitle, String newContent) {
        this.title = newTitle;
        this.content = newContent;
    }

    /**
     * 모집 글을 마감 상태로 변경합니다.
     *
     * <p>
     * 마감된 모집 글은 더 이상 지원을 받을 수 없으며,
     * 상태는 {@link RecruitPostStatus#CLOSED}로 변경됩니다.
     * </p>
     */
    public void close() { this.status = RecruitPostStatus.CLOSED; }
    /**
     * 모집 글을 삭제(비활성화) 상태로 변경합니다.
     *
     * <p>
     * 실제 데이터 삭제가 아닌,
     * 상태를 {@link RecruitPostStatus#DELETED}로 변경하여
     * 논리적으로 모집 글을 제거합니다.
     * </p>
     */
    public void delete(){ this.status=RecruitPostStatus.DELETED;}
}
