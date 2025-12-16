package org.example.buskmate.community.dto.post.crud.request;

/**
 * 게시글 수정 요청 DTO (record)
 * - 제목/본문 변경 값을 전달한다.
 */
public record CommunityPostUpdatePostRequest(
        String title,
        String content
) { }
