package org.example.buskmate.community.domain;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

/**
 * 커뮤니티 게시글 엔티티
 * - 작성자/제목/본문/상태(활성/삭제)를 관리한다.
 * - @Version을 통해 낙관적 락 기반 동시성 제어를 지원한다.
 */
@Getter
@Table(name = "community_post")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class CommunityPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "author_id", nullable = false, updatable = false)
    private String authorId;

    @Column(nullable = false)
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 16)
    private PostStatus isActive;

    @Column(nullable = false)
    private String content;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = true)
    private LocalDateTime updatedAt;

    @Version
    @Column(nullable = false)
    private Long version;

    /**
     * 게시글 엔티티 생성자(Builder)
     * - 제목/작성자/본문/상태를 초기화한다.
     */
    @Builder
    private CommunityPost(
            String title,
            String authorId,
            String content,
            PostStatus isActive
    ) {
        this.title = title;
        this.authorId = authorId;
        this.content = content;
        this.isActive = isActive;
    }

    /**
     * 게시글 생성용 정적 팩토리 메서드
     * - 생성 시 기본 상태를 ACTIVE로 설정한다.
     */
    public static CommunityPost createPost(String title, String authorId, String content) {
        CommunityPost createPost = CommunityPost.builder()
                .title(title)
                .authorId(authorId)
                .content(content)
                .build();

        createPost.isActive = PostStatus.ACTIVE;
        return createPost;
    }

    /**
     * 게시글 제목/본문을 수정한다.
     */
    public void updatePost(String title, String content) {
        this.title = title;
        this.content = content;
    }

    /**
     * 게시글을 소프트 삭제 상태로 변경한다.
     */
    public void softDelete(){
        this.isActive = PostStatus.DELETED;
    }

}