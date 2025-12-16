package org.example.buskmate.community.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

/**
 * 게시글 본문 변경 이력을 저장하는 엔티티
 * - 게시글 수정 시점의 버전(postVersion)과 본문(content)을 기록한다.
 */
@Entity
@Getter
@Table(name = "community_post_data_history")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommunityPostDataHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "community_post_id", nullable = false)
    private CommunityPost communityPost;

    @Column(name = "post_version", nullable = false)
    private Long postVersion;


    @Column(nullable = false)
    private String content;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    /**
     * 히스토리 엔티티 생성자(Builder)
     * - 게시글/버전/본문을 저장한다.
     */
    @Builder
    private CommunityPostDataHistory(
            CommunityPost communityPost,
            Long postVersion,
            String content
    ) {
        this.communityPost = communityPost;
        this.postVersion = postVersion;
        this.content = content;
    }

    /**
     * 게시글/버전/본문을 기반으로 히스토리 엔티티를 생성한다.
     */
    public static CommunityPostDataHistory from(
            CommunityPost post,
            Long postVersion,
            String content
    ) {
        return CommunityPostDataHistory.builder()
                .communityPost(post)
                .postVersion(postVersion)
                .content(content)
                .build();
    }
}
