package org.example.buskmate.community.dto.crud.request;

public record CreatePostRequest(
        String title,
        String authorId,
        String content
) { }
