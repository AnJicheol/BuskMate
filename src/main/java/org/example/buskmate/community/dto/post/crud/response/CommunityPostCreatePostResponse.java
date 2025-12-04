package org.example.buskmate.community.dto.post.crud.response;

import org.example.buskmate.community.domain.CommunityPost;

import java.time.LocalDateTime;

public record CommunityPostCreatePostResponse(
        String authorId,
        Long id,
        String title,
        LocalDateTime createdAt
) {
    public static CommunityPostCreatePostResponse of(CommunityPost c){
        return new CommunityPostCreatePostResponse(
                c.getAuthorId(),
                c.getId(),
                c.getTitle(),
                c.getCreatedAt()
        );
    }
}
