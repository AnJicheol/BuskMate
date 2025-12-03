package org.example.buskmate.community.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Table(name = "community_post_data")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class CommunityPostData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "community_post_id", nullable = false)
    private CommunityPost communityPost;


    @Column(nullable = true)
    private String mediaType;

    @Column(nullable = false)
    private String content;

    @Column(nullable = true)
    private Integer sortOrder;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Builder
    private CommunityPostData(
            CommunityPost communityPost,
            String mediaType,
            Integer sortOrder
    )
    {
        this.communityPost = communityPost;
        this.mediaType = mediaType;
        this.sortOrder = sortOrder;
    }

    public void updateContent(String content) {
        this.content = content;
    }

    public void updateSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }
}





// 노션처럼 블럭단위로 정렬하면 중간에 이미지가 들어가건 맨 위에 뭐가 추가되건 상관이 없다