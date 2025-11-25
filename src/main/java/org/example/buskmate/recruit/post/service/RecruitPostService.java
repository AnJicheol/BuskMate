package org.example.buskmate.recruit.post.service;

import org.example.buskmate.recruit.post.dto.CreateRecruitPostRequestDto;
import org.example.buskmate.recruit.post.dto.CreateRecruitPostResponseDto;

public interface RecruitPostService {
    CreateRecruitPostResponseDto create(CreateRecruitPostRequestDto req, String currentUserId);

}
