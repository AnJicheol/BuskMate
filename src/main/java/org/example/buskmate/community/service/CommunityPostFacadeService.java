package org.example.buskmate.community.service;

import lombok.RequiredArgsConstructor;
import org.example.buskmate.community.domain.CommunityPost;
import org.example.buskmate.community.dto.CommunityCommentResponseDto;
import org.example.buskmate.community.dto.post.crud.response.CommunityPostReadPostResponse;
import org.example.buskmate.community.repository.CommunityPostRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommunityPostFacadeService {
    private final CommunityPostRepository postRepository;
    private final CommunityCommentService commentService;
    private final CommunityPostLogService logService;

    /**
     * 특정 게시물 조회(조회수, 댓글수 포함)
     */

    @Transactional
    public CommunityPostReadPostResponse getPostWithCommentAndView(String viewerId, Long postId) {
        // 게시글 조회
        CommunityPost post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시물 입니다."));

        Long viewCount = logService.recordAndGetViewCount(viewerId, post);

        List<CommunityCommentResponseDto> comments = commentService.getCommentsByPostId(postId);

        return CommunityPostReadPostResponse.of(post, comments, viewCount);
    }
}
