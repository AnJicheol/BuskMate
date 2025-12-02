package org.example.buskmate.community.service;

import org.example.buskmate.community.dto.PostBlockRequestDto;
import org.example.buskmate.community.dto.PostBlockResponseDto;

import java.util.List;

public interface CommunityPostMediaService {

    /**
     * 특정 게시글의 블록 목록 조회
     */
    List<PostBlockResponseDto> getBlocksByPostId(Long postId);

    /**
     * 특정 게시글에 블록 하나 추가
     */
    Long addBlock(Long postId, PostBlockRequestDto request);

    /**
     * 블록 내용/타입/순서 수정
     */
    void updateBlock(Long blockId, PostBlockRequestDto request);

    /**
     * 블록 삭제
     */
    void deleteBlock(Long blockId);
}
