package org.example.buskmate.community.dto.crud.response;

import org.example.buskmate.community.domain.CommunityPost;

public record UpdatePostResponse(
        String authorId,
        String id,
        String title
) {
    public static UpdatePostResponse of(CommunityPost c){
        return new UpdatePostResponse(
                c.getAuthorId(),
                c.getId().toString(),
                c.getTitle()
        );
    }
}
