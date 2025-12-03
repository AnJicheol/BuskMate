package org.example.buskmate.community.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

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
