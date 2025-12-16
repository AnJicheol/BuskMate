package org.example.buskmate.community.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
/**
 * 댓글 생성 요청 DTO
 * - 댓글 본문(content)을 전달한다.
 */
@Getter
@NoArgsConstructor
public class CommunityCommentCreateRequestDto {


    private String content;

    public CommunityCommentCreateRequestDto(String content) {
        this.content = content;
    }
}
