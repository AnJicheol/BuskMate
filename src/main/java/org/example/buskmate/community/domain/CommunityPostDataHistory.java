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
    private String mediaType;

    @Column(nullable = false)
    private String content;

    @Column(nullable = true)
    private Integer sortOrder;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Builder
    private CommunityPostDataHistory(
            CommunityPost communityPost,
            Long postVersion,
            String mediaType,
            String content,
            Integer sortOrder
    ) {
        this.communityPost = communityPost;
        this.postVersion = postVersion;
        this.mediaType = mediaType;
        this.content = content;
        this.sortOrder = sortOrder;
    }

    public static CommunityPostDataHistory from(CommunityPostData data, Long postVersion) {
        return CommunityPostDataHistory.builder()
                .communityPost(data.getCommunityPost())
                .postVersion(postVersion)
                .mediaType(data.getMediaType())
                .content(data.getContent())
                .sortOrder(data.getSortOrder())
                .build();
    }
}
