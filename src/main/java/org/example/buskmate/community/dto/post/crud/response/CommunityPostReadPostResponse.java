package org.example.buskmate.community.dto.post.crud.response;

import org.example.buskmate.community.domain.CommunityComment;
import org.example.buskmate.community.domain.CommunityPost;
import org.example.buskmate.community.dto.CommunityCommentResponseDto;
import org.example.buskmate.community.repository.CommunityCommentRepository;

import java.util.List;

public record CommunityPostReadPostResponse(
        String authorId,
        String title,
        String content,
        long viewCount,
        List<CommunityCommentResponseDto> comments
)
{
    public static CommunityPostReadPostResponse of(CommunityPost c, List<CommunityComment> comments){
        return new CommunityPostReadPostResponse(
                c.getAuthorId(),
                c.getTitle(),
                c.getContent(),
                c.getViewCount(),
                comments.stream()
                        .map(CommunityCommentResponseDto:: from)
                        .toList()
        );
    }
}
