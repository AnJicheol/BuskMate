package org.example.buskmate.band.service;

import org.example.buskmate.band.dto.recruitapplication.RecruitApplicationListItemDto;
import org.example.buskmate.band.dto.recruitapplication.RecruitApplyResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RecruitApplicationService {

    RecruitApplyResponseDto apply(String postId, String currentUserId);

    void delete(String applicationId, String currentUserId);

    void accept(String applicationId, String currentUserId);

    void reject(String applicationId, String currentUserId);

    Page<RecruitApplicationListItemDto> getApplications(String postId, String currentUserId, Pageable pageable);
}
