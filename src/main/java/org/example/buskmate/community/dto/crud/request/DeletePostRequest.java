package org.example.buskmate.community.dto.crud.request;

public record DeletePostRequest(
        Integer id,
        String authorId
) { }
