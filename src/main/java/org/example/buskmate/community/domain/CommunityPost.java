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

    @Column(nullable = false)
    private String authorId;

    @Column(nullable = false)
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 16)
    private DeleteStatus isDeleted;

    @Version
    @Column(nullable = false)
    private Long version;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Builder
    private CommunityPost(
            String title,
            DeleteStatus isDeleted
    )
    {
        this.title = title;
        this.isDeleted = isDeleted;
    }

    // 필요한 메서드만 열어두기
    public void updatePost(String title) {
        this.title = title;
    }

    public void softDelete(){
        this.isDeleted = DeleteStatus.DELETED;
    }
}