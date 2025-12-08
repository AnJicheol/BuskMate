package org.example.buskmate.band.service;

import org.example.buskmate.band.dto.recruitpost.*;

import java.util.List;

public interface RecruitPostService {
    CreateRecruitPostResponseDto create(CreateRecruitPostRequestDto req, String currentUserId);

    RecruitPostDetailResponseDto getDetail(String postId);

    List<RecruitPostListDto> getActiveList();

    RecruitPostDetailResponseDto update(String postId, UpdateRecruitPostRequestDto req, String currentUserId);

    RecruitPostStatusResponseDto close(String postId, String currentUserId);

    RecruitPostStatusResponseDto delete(String postId, String currentUserId);
}
