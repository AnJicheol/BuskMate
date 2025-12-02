package org.example.buskmate.community.dto.crud.response;

import org.example.buskmate.community.domain.CommunityPost;

public record DeletePostResponse(
        Long id,
        String authorId
) {
    public static DeletePostResponse of(CommunityPost c){
        return new DeletePostResponse(
                c.getId(),
                c.getAuthorId()
        );
    }
}
