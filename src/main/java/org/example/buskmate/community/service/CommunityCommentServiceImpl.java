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

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommunityCommentServiceImpl implements CommunityCommentService {

    private final CommunityCommentRepository commentRepository;
    private final CommunityPostRepository postRepository;

    @Override
    public List<CommunityCommentResponseDto> getCommentsByPostId(Long postId) {
        List<CommunityComment> comments =
                commentRepository.findByCommunityPostIdAndisActiveOrderByCreatedAtAsc(
                        postId,
                        PostStatus.ACTIVE
                );

        return comments.stream()
                .map(CommunityCommentResponseDto::from)
                .toList();
    }

    @Override
    @Transactional
    public CommunityCommentResponseDto createComment(Long postId, String authorId, CommunityCommentCreateRequestDto requestDto) {
        CommunityPost post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("CommunityPost not found: " + postId));

        CommunityComment comment = CommunityComment.builder()
                .communityPost(post)
                .authorId(authorId) //
                .content(requestDto.getContent())
                .isActive(PostStatus.ACTIVE)
                .build();

        CommunityComment saved = commentRepository.save(comment);
        return CommunityCommentResponseDto.from(saved);
    }

    @Override
    @Transactional
    public CommunityCommentResponseDto updateComment(Long commentId, CommunityCommentUpdateRequestDto requestDto) {
        CommunityComment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException("Comment not found: " + commentId));

        comment.updateComment(requestDto.getContent());

        return CommunityCommentResponseDto.from(comment);
    }

    @Override
    @Transactional
    public void deleteComment(Long commentId) {
        CommunityComment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException("Comment not found: " + commentId));

        comment.softDelete();

    }

    // Post당 댓글 수 조회
    @Transactional(readOnly =true)
    public Long countByCommunityPostId(Long postId) {
        return commentRepository.countByCommunityPostIdAndisActive(postId, PostStatus.ACTIVE);
    }
}
