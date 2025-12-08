package org.example.buskmate.community.dto.post.crud.response;

import org.example.buskmate.community.domain.CommunityPost;
import org.example.buskmate.community.dto.CommunityCommentResponseDto;

import java.util.List;

public record CommunityPostReadPostResponse(
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
                c.getAuthorId(),
                c.getTitle(),
                c.getContent(),
                comments,
                viewCount
        );
    }
}
