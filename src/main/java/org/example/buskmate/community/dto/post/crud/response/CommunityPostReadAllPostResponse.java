package org.example.buskmate.community.dto.post.crud.response;

import org.example.buskmate.community.domain.CommunityPost;

public record CommunityPostReadAllPostResponse(
        String authorId,
        String title,
        String content,
        long viewCount,
        long commentCount
)
{
    public static CommunityPostReadAllPostResponse of(CommunityPost c, int commentCount){
        return new CommunityPostReadAllPostResponse(
                c.getAuthorId(),
                c.getTitle(),
                c.getContent(),
                c.getViewCount(),
                commentCount
        );
    }
}
