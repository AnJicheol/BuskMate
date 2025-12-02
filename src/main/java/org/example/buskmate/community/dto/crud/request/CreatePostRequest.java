package org.example.buskmate.community.dto.crud.request;

import org.example.buskmate.community.domain.CommunityPost;

public record CreatePostRequest(
        String authorId,
        String title
) { }
