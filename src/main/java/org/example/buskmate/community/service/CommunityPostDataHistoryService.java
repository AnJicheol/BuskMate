package org.example.buskmate.community.service;

import org.example.buskmate.community.domain.CommunityPost;
import org.example.buskmate.community.dto.CommunityPostDataHistoryResponseDto;

import java.util.List;

public interface CommunityPostDataHistoryService {

    // 특정 게시글의 히스토리 목록 조회
    List<CommunityPostDataHistoryResponseDto> getHistoriesByPostId(Long postId);

    // 게시글 히스토리 한 건 저장 (게시글 수정 시 호출)
    void saveHistory(CommunityPost post, String content);
}
