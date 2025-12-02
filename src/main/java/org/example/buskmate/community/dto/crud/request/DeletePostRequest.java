package org.example.buskmate.community.dto.crud.request;

import org.example.buskmate.community.domain.CommunityPost;
import org.example.buskmate.community.dto.crud.response.DeletePostResponse;

public record DeletePostRequest(
        Long id,
        String authorId
) { }
