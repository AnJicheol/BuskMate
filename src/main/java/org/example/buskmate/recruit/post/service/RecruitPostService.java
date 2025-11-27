package org.example.buskmate.recruit.post.service;

import org.example.buskmate.recruit.post.dto.*;

import java.util.List;

public interface RecruitPostService {
    CreateRecruitPostResponseDto create(CreateRecruitPostRequestDto req, String currentUserId);

    RecruitPostDetailResponseDto getDetail(String postId);

    List<RecruitPostListDto> getActiveList();

    RecruitPostDetailResponseDto update(String postId, UpdateRecruitPostRequestDto req, String currentUserId);

    RecruitPostStatusResponseDto close(String postId, String currentUserId);

    RecruitPostStatusResponseDto delete(String postId, String currentUserId);

}
