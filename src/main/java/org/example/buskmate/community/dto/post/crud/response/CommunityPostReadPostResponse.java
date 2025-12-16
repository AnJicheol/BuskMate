package org.example.buskmate.community.dto.post.crud.response;

import org.example.buskmate.community.domain.CommunityPost;
import org.example.buskmate.community.dto.CommunityCommentResponseDto;

import java.util.List;
/**
 * 게시글 단건 조회 응답 DTO (record)
 * - 게시글 본문 + 댓글 목록 + 조회수 정보를 포함한다.
 */
public record CommunityPostReadPostResponse(
        Long id,
        String authorId,
        String title,
        String content,
        List<CommunityCommentResponseDto> comments,
        Long viewCount
) {
    public static CommunityPostReadPostResponse of(
            CommunityPost c,
            List<CommunityCommentResponseDto> comments,
            Long viewCount
    ){
        return new CommunityPostReadPostResponse(
                c.getId(),
                c.getAuthorId(),
                c.getTitle(),
                c.getContent(),
                comments,
                viewCount
        );
    }
}
