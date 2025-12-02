package org.example.buskmate.community.dto.crud.response;

import org.example.buskmate.community.domain.CommunityPost;
import org.example.buskmate.community.dto.crud.request.CreatePostRequest;

import java.time.LocalDateTime;

public record CreatePostResponse(
        String authorId,
        Long id,
        String title,
        LocalDateTime createdAt
) {
    public static CreatePostResponse of(CommunityPost c){
        return new CreatePostResponse(
                c.getAuthorId(),
                c.getId(),
                c.getTitle(),
                c.getCreatedAt()
        );
    }
}
