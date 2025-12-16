package org.example.buskmate.community.service;

import lombok.RequiredArgsConstructor;
import org.example.buskmate.community.domain.CommunityPost;
import org.example.buskmate.community.dto.CommunityCommentResponseDto;
import org.example.buskmate.community.dto.post.crud.response.CommunityPostReadPostResponse;
import org.example.buskmate.community.repository.CommunityPostRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 게시글 조회 파사드 서비스
 * - 게시글 단건 조회 시 조회수 기록/집계와 댓글 조회를 결합하여 응답을 만든다.
 */
@Service
@RequiredArgsConstructor
public class CommunityPostFacadeService {

    private final CommunityPostRepository postRepository;
    private final CommunityCommentService commentService;
    private final CommunityPostLogService logService;

    /**
     * 게시글을 조회하면서 조회수 기록/집계 및 댓글 목록을 포함한 응답 DTO를 반환한다.
     */
    @Transactional
    public CommunityPostReadPostResponse getPostWithCommentAndView(String viewerId, Long postId) {
        CommunityPost post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시물 입니다."));

        Long viewCount = logService.recordAndGetViewCount(viewerId, post);

        List<CommunityCommentResponseDto> comments = commentService.getCommentsByPostId(postId);

        return CommunityPostReadPostResponse.of(post, comments, viewCount);
    }
}
