package org.example.buskmate.community.dto.post.crud.response;

import org.example.buskmate.community.domain.CommunityPost;

import java.time.LocalDateTime;

public record CommunityPostReadAllPostResponse(
        Long postId,
        String authorId,
        String title,
        Long viewCount,
        LocalDateTime displayTime,
        Long chatCount
) {
    public static CommunityPostReadAllPostResponse of(
            CommunityPost c,
            Long viewCount,
            LocalDateTime displayTime,
            Long chatCount
    ){
        return new CommunityPostReadAllPostResponse(
                c.getId(),
                c.getAuthorId(),
                c.getTitle(),
                viewCount,
                displayTime,
                chatCount
        );
    }
}
