package org.example.buskmate.community.service;

import lombok.RequiredArgsConstructor;
import org.example.buskmate.community.domain.CommunityPost;
import org.example.buskmate.community.domain.CommunityPostLog;
import org.example.buskmate.community.repository.CommunityPostLogRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommunityPostLogService {
    
    private final CommunityPostLogRepository communityPostLogRepo;

    // 1. 조회수 증가
    @Transactional
    public void checkAndRecordView(
            String viewerId,
            CommunityPost post
    ) {
        boolean viewedRecently = communityPostLogRepo
                .findTop1ByCommunityPostIdAndViewerIdOrderByViewedAtDesc(post.getId(), viewerId)
                .isPresent();
        if (viewedRecently) return;

        CommunityPostLog newLog = CommunityPostLog.builder()
                .communityPost(post)
                .viewerId(viewerId)
                .build();

        communityPostLogRepo.save(newLog);
    }

    // 2. 전체 게시물에 대한 조회수 리턴
    @Transactional(readOnly = true)
    public Long getViewCount(CommunityPost post){
        return communityPostLogRepo.countByCommunityPostId(post.getId());
    }
}
