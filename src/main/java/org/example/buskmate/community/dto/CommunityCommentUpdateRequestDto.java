package org.example.buskmate.community.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
/**
 * 댓글 수정 요청 DTO
 * - 수정할 댓글 본문(content)을 전달한다.
 */
@Getter
@NoArgsConstructor
public class CommunityCommentUpdateRequestDto {

    private String content;

    public CommunityCommentUpdateRequestDto(String content) {
        this.content = content;
    }
}
