package org.example.buskmate.community.dto.post.crud.request;


public record CommunityPostReadAllPostRequest(
        String authorId,
        String title,
        int page,
        int size,
        String sortBy,
        boolean desc
) { }
