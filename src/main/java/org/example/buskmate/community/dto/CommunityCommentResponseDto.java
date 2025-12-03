package org.example.buskmate.community.dto;

import lombok.Builder;
import lombok.Getter;
import org.example.buskmate.community.domain.CommunityComment;

import java.time.LocalDateTime;

@Getter
public class CommunityCommentResponseDto {

    private final Long id;
    private final Long postId;
    private final String authorId;
    private final String content;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private final Long version;

    @Builder
    private CommunityCommentResponseDto(
            Long id,
            Long postId,
            String authorId,
            String content,
            LocalDateTime createdAt,
            LocalDateTime updatedAt,
            Long version
    ) {
        this.id = id;
        this.postId = postId;
        this.authorId = authorId;
        this.content = content;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.version = version;
    }

    public static CommunityCommentResponseDto from(CommunityComment comment) {
        return CommunityCommentResponseDto.builder()
                .id(comment.getId())
                .postId(comment.getCommunityPost().getId())
                .authorId(comment.getAuthorId())
                .content(comment.getContent())
                .createdAt(comment.getCreatedAt())
                .updatedAt(comment.getUpdatedAt())
                .version(comment.getVersion())
                .build();
    }
}

