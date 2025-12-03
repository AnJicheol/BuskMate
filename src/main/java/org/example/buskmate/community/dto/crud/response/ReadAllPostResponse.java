package org.example.buskmate.community.dto.crud.response;

import org.example.buskmate.community.domain.CommunityPost;

public record ReadAllPostResponse(
        String authorId,
        String title
)
{
    public static ReadAllPostResponse of(CommunityPost c){
        return new ReadAllPostResponse(
                c.getAuthorId(),
                c.getTitle()
        );
    }
}
