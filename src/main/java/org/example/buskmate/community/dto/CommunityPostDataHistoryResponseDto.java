package org.example.buskmate.community.dto;

import lombok.Builder;
import lombok.Getter;
import org.example.buskmate.community.domain.CommunityPostDataHistory;

import java.time.LocalDateTime;

@Getter
public class CommunityPostDataHistoryResponseDto {

    private final Long id;
    private final Long postId;
    private final Long postVersion;
    private final String content;
    private final LocalDateTime createdAt;

    @Builder
    private CommunityPostDataHistoryResponseDto(
            Long id,
            Long postId,
            Long postVersion,
            String content,
            LocalDateTime createdAt
    ) {
        this.id = id;
        this.postId = postId;
        this.postVersion = postVersion;
        this.content = content;
        this.createdAt = createdAt;
    }

    public static CommunityPostDataHistoryResponseDto from(CommunityPostDataHistory history) {
        return CommunityPostDataHistoryResponseDto.builder()
                .id(history.getId())
                .postId(history.getCommunityPost().getId())
                .postVersion(history.getPostVersion())
                .content(history.getContent())
                .createdAt(history.getCreatedAt())
                .build();
    }
}
