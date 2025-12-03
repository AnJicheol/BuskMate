package org.example.buskmate.community.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommunityCommentCreateRequestDto {


    private String content;

    public CommunityCommentCreateRequestDto(String content) {
        this.content = content;
    }
}
