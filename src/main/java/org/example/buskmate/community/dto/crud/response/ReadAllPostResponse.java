package org.example.buskmate.community.dto.crud.response;

import org.example.buskmate.community.domain.CommunityPost;

public record ReadAllPostResponse(
        String authorId,
        String title,
        int commentCount
)
{
    public static ReadAllPostResponse of(CommunityPost c, int commentCount){
        return new ReadAllPostResponse(
                c.getAuthorId(),
                c.getTitle(),
                commentCount
        );
    }
}
