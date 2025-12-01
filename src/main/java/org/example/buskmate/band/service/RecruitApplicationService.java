package org.example.buskmate.band.service;

import org.example.buskmate.band.dto.recruitapplication.RecruitApplyResponseDto;

public interface RecruitApplicationService {

    RecruitApplyResponseDto apply(String postId, String currentUserId);

    void delete(String applicationId, String currentUserId);

    void accept(String applicationId, String currentUserId);

    void reject(String applicationId, String currentUserId);
}
