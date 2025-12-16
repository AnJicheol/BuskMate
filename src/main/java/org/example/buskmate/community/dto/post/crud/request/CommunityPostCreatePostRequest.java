package org.example.buskmate.community.dto.post.crud.request;
/**
 * 게시글 생성 요청 DTO (record)
 * - 제목/본문을 전달한다.
 */
public record CommunityPostCreatePostRequest(
        String title,
        String content
) { }
