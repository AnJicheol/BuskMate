package org.example.buskmate.community.dto.post.crud.request;

public record CommunityPostReadPostRequest(
        String authorId,
        String title,
        String content
) { }
