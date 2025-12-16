package org.example.buskmate.community.dto.post.crud.response;

import org.example.buskmate.community.domain.CommunityPost;

import java.time.LocalDateTime;
/**
 * 게시글 목록 조회용 요약 응답 DTO (record)
 * - 목록 화면에서 필요한 요약 정보(조회수/댓글수 포함)를 제공한다.
 */
public record CommunityPostReadAllPostResponse(
        Long postId,
        String authorId,
        String title,
        Long viewCount,
        LocalDateTime createdAt,
        Long chatCount
) {
    public static CommunityPostReadAllPostResponse of(
            CommunityPost c,
            Long viewCount,
            LocalDateTime createdAt,
            Long chatCount
    ){
        return new CommunityPostReadAllPostResponse(
                c.getId(),
                c.getAuthorId(),
                c.getTitle(),
                viewCount,
                createdAt,
                chatCount
        );
    }
}
