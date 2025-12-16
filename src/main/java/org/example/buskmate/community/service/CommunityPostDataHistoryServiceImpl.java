package org.example.buskmate.community.service;

import lombok.RequiredArgsConstructor;
import org.example.buskmate.community.domain.CommunityPost;
import org.example.buskmate.community.domain.CommunityPostDataHistory;
import org.example.buskmate.community.dto.CommunityPostDataHistoryResponseDto;
import org.example.buskmate.community.repository.CommunityPostDataHistoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 게시글 데이터 히스토리 서비스 구현체
 * - 게시글 수정 이력을 저장하고 조회한다.
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommunityPostDataHistoryServiceImpl implements CommunityPostDataHistoryService{

    private final CommunityPostDataHistoryRepository historyRepository;

    /**
     * 특정 게시글의 히스토리 목록을 최신순으로 조회해 응답 DTO로 반환한다.
     */
    @Override
    public List<CommunityPostDataHistoryResponseDto> getHistoriesByPostId(Long postId) {
        List<CommunityPostDataHistory> histories =
                historyRepository.findByCommunityPostIdOrderByCreatedAtDesc(postId);

        return histories.stream()
                .map(CommunityPostDataHistoryResponseDto::from)
                .toList();
    }

    /**
     * 게시글/버전/본문을 기반으로 히스토리 엔티티를 생성해 저장한다.
     */
    @Override
    @Transactional
    public void saveHistory(CommunityPost post, String content) {
        CommunityPostDataHistory history = CommunityPostDataHistory.from(
                post,
                post.getVersion(),
                content
        );

        historyRepository.save(history);
    }
}
