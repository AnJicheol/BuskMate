package org.example.buskmate.community.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

/**
 * 커뮤니티 댓글 엔티티
 * - 게시글(CommunityPost)에 종속되며, 작성자/내용/상태(활성/삭제)를 관리한다.
 * - @Version을 통해 낙관적 락 기반 동시성 제어를 지원한다.
 */
@Getter
@Table(name = "community_comment")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class CommunityComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "community_post_id", nullable = false)
    private CommunityPost communityPost;

    @Column(nullable = false)
    private String authorId;

    @Column(nullable = false)
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 16)
    private PostStatus isActive;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "update_at", nullable = false)
    private LocalDateTime updatedAt;

    @Version
    @Column(nullable = false)
    private Long version;

    /**
     * 댓글 엔티티 생성자(Builder)
     * - 게시글/작성자/내용/상태를 초기화한다.
     */
    @Builder
    private CommunityComment(
            CommunityPost communityPost,
            String authorId,
            String content,
            PostStatus isActive
    ) {
        this.communityPost = communityPost;
        this.authorId = authorId;
        this.content = content;
        this.isActive = isActive;
    }

    /**
     * 댓글 내용을 수정한다.
     */
    public void updateComment(String content) {
        this.content = content;
    }

    /**
     * 댓글을 소프트 삭제 상태로 변경한다.
     */
    public void softDelete() {
        this.isActive = PostStatus.DELETED;
    }
}
