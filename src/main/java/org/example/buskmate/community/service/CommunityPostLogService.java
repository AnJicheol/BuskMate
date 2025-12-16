package org.example.buskmate.community.service;

import lombok.RequiredArgsConstructor;
import org.example.buskmate.community.domain.CommunityPost;
import org.example.buskmate.community.domain.CommunityPostLog;
import org.example.buskmate.community.repository.CommunityPostLogRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 게시글 조회 로그 서비스
 * - 조회 로그를 기록하고, 해당 게시글의 조회수를 집계하여 반환한다.
 */
@Service
@RequiredArgsConstructor
public class CommunityPostLogService {

    private final CommunityPostLogRepository communityPostLogRepo;

    /**
     * 조회 로그를 기록하고 게시글의 고유 조회자 기준 조회수를 반환한다.
     */
    @Transactional
    public Long recordAndGetViewCount(String viewerId, CommunityPost post){

        boolean viewedRecently = communityPostLogRepo
                .findTop1ByCommunityPostIdAndViewerIdOrderByViewedAtDesc(post.getId(), viewerId)
                .isPresent();

        if(!viewedRecently) {
            CommunityPostLog newLog = CommunityPostLog.builder()
                    .communityPost(post)
                    .viewerId(viewerId)
                    .build();
            communityPostLogRepo.save(newLog);
        }

        return communityPostLogRepo.countByCommunityPostId(post.getId());
    }
}
