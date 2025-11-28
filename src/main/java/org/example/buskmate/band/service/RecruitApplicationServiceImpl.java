package org.example.buskmate.band.service;

import lombok.RequiredArgsConstructor;
import org.example.buskmate.band.dto.recruitapplication.RecruitApplyResponseDto;
import org.example.buskmate.band.repository.RecruitApplicationRepository;
import org.example.buskmate.band.repository.RecruitPostRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor

public class RecruitApplicationServiceImpl implements RecruitApplicationService {
    private final RecruitApplicationRepository recruitApplicationRepository;
    private final RecruitPostRepository recruitPostRepository;

    @Transactional
    @Override
    public RecruitApplyResponseDto apply(String postId, String currentUserId){

    }
}
