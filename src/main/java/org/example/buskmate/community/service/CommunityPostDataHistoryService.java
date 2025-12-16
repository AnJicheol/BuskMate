package org.example.buskmate.community.service;

import org.example.buskmate.community.domain.CommunityPost;
import org.example.buskmate.community.dto.CommunityPostDataHistoryResponseDto;

import java.util.List;

/**
 * 게시글 데이터 히스토리 서비스 인터페이스
 * - 게시글 수정 이력 조회 및 히스토리 저장 기능을 정의한다.
 */
public interface CommunityPostDataHistoryService {

    /**
     * 특정 게시글의 히스토리 목록을 조회한다.
     */
    List<CommunityPostDataHistoryResponseDto> getHistoriesByPostId(Long postId);

    /**
     * 게시글 수정 시점의 히스토리 한 건을 저장한다.
     */
    void saveHistory(CommunityPost post, String content);
}
