package org.example.buskmate.community.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.buskmate.community.domain.CommunityComment;
import org.example.buskmate.community.domain.CommunityPost;
import org.example.buskmate.community.domain.PostStatus;
import org.example.buskmate.community.dto.CommunityCommentCreateRequestDto;
import org.example.buskmate.community.dto.CommunityCommentResponseDto;
import org.example.buskmate.community.dto.CommunityCommentUpdateRequestDto;
import org.example.buskmate.community.repository.CommunityCommentRepository;
import org.example.buskmate.community.repository.CommunityPostRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 댓글 서비스 구현체
 * - 댓글 CRUD 및 소프트 삭제 로직을 처리한다.
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommunityCommentServiceImpl implements CommunityCommentService {

    private final CommunityCommentRepository commentRepository;
    private final CommunityPostRepository postRepository;

    /**
     * 특정 게시글의 활성 댓글을 시간순으로 조회해 응답 DTO로 반환한다.
     */
    @Override
    public List<CommunityCommentResponseDto> getCommentsByPostId(Long postId) {
        List<CommunityComment> comments =
                commentRepository.findByCommunityPostIdAndIsActiveOrderByCreatedAtAsc(
                        postId,
                        PostStatus.ACTIVE
                );

        return comments.stream()
                .map(CommunityCommentResponseDto::from)
                .toList();
    }

    /**
     * 특정 게시글을 조회한 뒤 댓글 엔티티를 생성/저장하고 응답 DTO로 반환한다.
     */
    @Override
    @Transactional
    public CommunityCommentResponseDto createComment(Long postId, String authorId, CommunityCommentCreateRequestDto requestDto) {
        CommunityPost post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("CommunityPost not found: " + postId));

        CommunityComment comment = CommunityComment.builder()
                .communityPost(post)
                .authorId(authorId)
                .content(requestDto.getContent())
                .isActive(PostStatus.ACTIVE)
                .build();

        CommunityComment saved = commentRepository.save(comment);
        return CommunityCommentResponseDto.from(saved);
    }

    /**
     * 댓글을 조회한 뒤 내용을 수정하고 응답 DTO로 반환한다.
     */
    @Override
    @Transactional
    public CommunityCommentResponseDto updateComment(Long commentId, CommunityCommentUpdateRequestDto requestDto) {
        CommunityComment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException("Comment not found: " + commentId));

        comment.updateComment(requestDto.getContent());

        return CommunityCommentResponseDto.from(comment);
    }

    /**
     * 댓글을 조회한 뒤 소프트 삭제 상태로 변경한다.
     */
    @Override
    @Transactional
    public void deleteComment(Long commentId) {
        CommunityComment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException("Comment not found: " + commentId));

        comment.softDelete();
    }

    /**
     * 특정 게시글의 활성 댓글들을 조회하여 일괄 소프트 삭제한다.
     */
    @Override
    @Transactional
    public void softDeleteCommentsByPostId(Long postId) {
        List<CommunityComment> comments =
                commentRepository.findByCommunityPostIdAndIsActiveOrderByCreatedAtAsc(postId, PostStatus.ACTIVE);

        comments.forEach(CommunityComment::softDelete);
    }
}
