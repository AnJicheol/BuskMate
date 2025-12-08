package org.example.buskmate.community.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

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
    private DeleteStatus isDeleted;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "update_at", nullable = false)
    private LocalDateTime updatedAt;

    @Version
    @Column(nullable = false)
    private Long version;

    @Builder
    private CommunityComment(
            CommunityPost communityPost,
            String authorId,
            String content,
            DeleteStatus isDeleted
    ) {
        this.communityPost = communityPost;
        this.authorId = authorId;
        this.content = content;
        this.isDeleted = isDeleted;
    }

    public void updateComment(String content) {
        this.content = content;
    }

    public void softDelete() {
        this.isDeleted = DeleteStatus.DELETED;
    }
}
