package org.example.buskmate.community.dto;

import lombok.Builder;
import lombok.Getter;
import org.example.buskmate.community.domain.CommunityPostDataHistory;

import java.time.LocalDateTime;
/**
 * 게시글 데이터 히스토리 응답 DTO
 * - 버전별 본문 변경 이력을 클라이언트 응답 형태로 변환한다.
 */
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
