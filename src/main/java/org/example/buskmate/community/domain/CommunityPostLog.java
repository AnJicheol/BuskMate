package org.example.buskmate.community.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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
