package org.example.buskmate.community.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommunityCommentUpdateRequestDto {

    private String content;

    public CommunityCommentUpdateRequestDto(String content) {
        this.content = content;
    }
}
