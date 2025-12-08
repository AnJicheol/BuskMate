package org.example.buskmate.community.dto.post.crud.response;

import org.example.buskmate.community.domain.CommunityPost;
import org.example.buskmate.community.dto.CommunityCommentResponseDto;

import java.time.LocalDateTime;
import java.util.List;

public record CommunityPostReadPostResponse(
        String authorId,
        String title,
        String content,
        List<CommunityCommentResponseDto> comments,
        Long viewCount,
        LocalDateTime displayTime
) {
    public static CommunityPostReadPostResponse of(
            CommunityPost c,
            List<CommunityCommentResponseDto> comments,
            Long viewCount,
            LocalDateTime disPlayTime
    ){
        return new CommunityPostReadPostResponse(
                c.getAuthorId(),
                c.getTitle(),
                c.getContent(),
                comments,
                viewCount,
                disPlayTime
        );
    }
}
