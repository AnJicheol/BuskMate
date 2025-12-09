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

    // 조회수를 증가시키고 게시물의 조회수를 리턴함
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
