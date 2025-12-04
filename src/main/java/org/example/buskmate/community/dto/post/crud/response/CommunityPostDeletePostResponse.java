package org.example.buskmate.community.dto.post.crud.response;

import org.example.buskmate.community.domain.CommunityPost;

public record CommunityPostDeletePostResponse(
        Long id,
        String authorId
) {
    public static CommunityPostDeletePostResponse of(CommunityPost c){
        return new CommunityPostDeletePostResponse(
                c.getId(),
                c.getAuthorId()
        );
    }
}
