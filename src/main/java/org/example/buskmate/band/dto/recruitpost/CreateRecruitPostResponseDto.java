package org.example.buskmate.band.dto.recruitpost;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateRecruitPostResponseDto {
    private String postId;
    private String bandId;
    private String title;
}
