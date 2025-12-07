package org.example.buskmate.community.service;

import lombok.RequiredArgsConstructor;
import org.example.buskmate.community.domain.CommunityPost;
import org.example.buskmate.community.domain.CommunityPostLog;
import org.example.buskmate.community.dto.post.crud.response.CommunityPostReadPostResponse;
import org.example.buskmate.community.repository.CommunityPostLogRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CommunityPostLogService {
    
    private final CommunityPostLogRepository communityPostLogRepo;

    // 동일 인물이 2분 내에 같은 글 조회시 조회수 증가 X
    private static final Long VIEW_LIMIT_MINUTES = 2L;

    // 1. 최근에 조회했다면 다시 조회수를 올리지 않음
    @Transactional
    public void checkAndRecordView(
            String viewerId,
            CommunityPost post
    )
    {
        // 1. 최근 로그 가져오기
        CommunityPostLog lastLog =
                communityPostLogRepo.findTop1ByCommunityPostIdAndViewerIdOrderByViewedAtDesc(
                        post.getId(), viewerId
                );

        // 2. 제한 시간 내 재조회되면 로그 기록 안함
        if(lastLog != null && lastLog.isViewedWithinMinutes(VIEW_LIMIT_MINUTES)){
            return;
        }

        // 3. 새로운 조회 로그 기록
        CommunityPostLog newLog = CommunityPostLog.builder()
                .communityPost(post)
                .viewerId(viewerId)
                .build();

        communityPostLogRepo.save(newLog);
    }

    @Transactional(readOnly = true)
    // 2. 전체 게시물에 대한 조회수 리턴
    public Long getViewCount(Long postId){
        return communityPostLogRepo.countByCommunityPostId(postId);
    }
}
