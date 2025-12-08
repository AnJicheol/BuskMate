package org.example.buskmate.community.service;

import lombok.RequiredArgsConstructor;
import org.example.buskmate.community.domain.CommunityPost;
import org.example.buskmate.community.domain.CommunityPostLog;
import org.example.buskmate.community.repository.CommunityPostLogRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommunityPostLogService {
    
    private final CommunityPostLogRepository communityPostLogRepo;

    private static final Long VIEW_LIMIT_MINUTES = 2L;

    // 1. 최근에 2분 이내에 조회한 경우를 제외하고, 조회수 증가
    @Transactional
    public void checkAndRecordView(
            String viewerId,
            CommunityPost post
    ) {
        Optional<CommunityPostLog> lastLogOpt =
                communityPostLogRepo.findTop1ByCommunityPostIdAndViewerIdOrderByViewedAtDesc(post.getId(), viewerId);

        if(lastLogOpt.map(log ->
                log.isViewedWithinMinutes(VIEW_LIMIT_MINUTES)).orElse(false)) {
            return;
        }

        CommunityPostLog newLog = CommunityPostLog.builder()
                .communityPost(post)
                .viewerId(viewerId)
                .build();

        communityPostLogRepo.save(newLog);
    }

    // 2. 전체 게시물에 대한 조회수 리턴
    @Transactional(readOnly = true)
    public Long getViewCount(Long postId){
        return communityPostLogRepo.countByCommunityPostId(postId);
    }
}
