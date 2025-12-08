package org.example.buskmate.community.domain;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Getter
@Table(name = "community_post")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class CommunityPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, updatable = false)
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

    // 필요한 메서드만 열어두기
    public static CommunityPost createPost(String title, String authorId, String content) {
        CommunityPost createPost = CommunityPost.builder()
                .title(title)
                .authorId(authorId)
                .content(content)
                .build();

        createPost.isActive = PostStatus.ACTIVE;
        return createPost;
    }

    // 본문 수정 시 호출
    public void updatePost(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void softDelete(){
        this.isActive = PostStatus.DELETED;
    }

}