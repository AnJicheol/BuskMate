package org.example.buskmate.community.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 게시글 조회 기록 엔티티
 * - 어떤 사용자가(viewerId) 어떤 게시글을 언제(viewedAt) 조회했는지 기록한다.
 * - 조회수 집계 시, viewerId 중복을 제거하여 고유 조회자를 기준으로 계산한다.
 */
@Entity
@Getter
@NoArgsConstructor
public class CommunityPostLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "community_post_id", nullable = false)
    private CommunityPost communityPost;

    @Column(nullable = false, updatable = false)
    private String viewerId;

    @Column(nullable = false, updatable = false)
    private LocalDateTime viewedAt;

    /**
     * 조회 로그 엔티티 생성자(Builder)
     * - 게시글/조회자 정보를 기반으로 로그를 생성한다.
     */
    @Builder
    private CommunityPostLog(
            CommunityPost communityPost,
            String viewerId
    ){
        this.communityPost = communityPost;
        this.viewerId = viewerId;
    }

    // 조회 시간
    @PrePersist
    public void viewChecking() {
        this.viewedAt = LocalDateTime.now();
    }
}
