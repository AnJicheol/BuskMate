package org.example.buskmate.community.dto.crud.request;

public record ReadPostRequest(
        String authorId,
        String title,
        String content
) { }
