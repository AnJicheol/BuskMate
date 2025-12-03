package org.example.buskmate.community.dto.crud.response;

import org.example.buskmate.community.domain.CommunityPost;

public record ReadPostResponse(
        String authorId,
        String title,
        String content,
        int viewCount
)
{
    public static ReadPostResponse of(CommunityPost c){
        return new ReadPostResponse(
                c.getAuthorId(),
                c.getTitle(),
                c.getContent(),
                c.getViewCount()
        );
    }
}
