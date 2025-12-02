package org.example.buskmate.community.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostBlockRequestDto {

    @NotBlank
    private String mediaType;

    @NotBlank
    private String content;

    private Integer sortOrder;

    @Builder
    public PostBlockRequestDto(String mediaType, String content, Integer sortOrder) {
        this.mediaType = mediaType;
        this.content = content;
        this.sortOrder = sortOrder;
    }

}
