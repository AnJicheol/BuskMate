package org.example.buskmate.community.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostBlockResponseDto {

    private Long id;
    private String mediaType;
    private String content;
    private Integer sortOrder;

    @Builder
    public PostBlockResponseDto(Long id, String mediaType, String content, Integer sortOrder) {
        this.id = id;
        this.mediaType = mediaType;
        this.content = content;
        this.sortOrder = sortOrder;
    }
}
