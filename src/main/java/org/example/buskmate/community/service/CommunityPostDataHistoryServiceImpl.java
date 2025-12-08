package org.example.buskmate.community.service;

import lombok.RequiredArgsConstructor;
import org.example.buskmate.community.domain.CommunityPost;
import org.example.buskmate.community.domain.CommunityPostDataHistory;
import org.example.buskmate.community.dto.CommunityPostDataHistoryResponseDto;
import org.example.buskmate.community.repository.CommunityPostDataHistoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommunityPostDataHistoryServiceImpl implements CommunityPostDataHistoryService{

    private final CommunityPostDataHistoryRepository historyRepository;

    @Override
    public List<CommunityPostDataHistoryResponseDto> getHistoriesByPostId(Long postId) {
        List<CommunityPostDataHistory> histories =
                historyRepository.findByCommunityPostIdOrderByCreatedAtDesc(postId);

        return histories.stream()
                .map(CommunityPostDataHistoryResponseDto::from)
                .toList();
    }

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
