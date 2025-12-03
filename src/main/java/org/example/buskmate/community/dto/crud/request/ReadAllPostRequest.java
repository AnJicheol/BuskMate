package org.example.buskmate.community.dto.crud.request;


public record ReadAllPostRequest(
        String authorId,
        String title,
        int page,
        int size,
        String sortBy,
        boolean desc
) { }
