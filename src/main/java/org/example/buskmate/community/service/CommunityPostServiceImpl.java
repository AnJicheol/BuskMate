package org.example.buskmate.community.service;

import lombok.RequiredArgsConstructor;
import org.example.buskmate.community.domain.CommunityPost;
import org.example.buskmate.community.domain.PostStatus;
import org.example.buskmate.community.dto.CommunityCommentResponseDto;
import org.example.buskmate.community.dto.post.crud.request.*;
import org.example.buskmate.community.dto.post.crud.response.CommunityPostReadAllPostResponse;
import org.example.buskmate.community.dto.post.crud.response.CommunityPostReadPostResponse;
import org.example.buskmate.community.exception.UnauthorizedPostAccessException;
import org.example.buskmate.community.repository.CommunityPostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommunityPostServiceImpl implements CommunityPostService {

    private final CommunityPostRepository communityPostRepo;
    private final CommunityCommentService communityCommentService;
    private final CommunityPostLogService communityPostLogService;

    // 1. 게시글 생성
    @Transactional
    public void createPost(CommunityPostCreatePostRequest request, String authorId){
        CommunityPost post = CommunityPost.createPost(
                request.title(),
                authorId,
                request.content()
        );

        communityPostRepo.save(post);
    }
    // 2. 전체 게시글 조회
    @Transactional(readOnly = true)
    public Page<CommunityPostReadAllPostResponse> getAllPost(
            Pageable pageable,
            String authorId,
            CommunityPostReadAllPostRequest request
    ){
        Page<CommunityPost> posts = communityPostRepo.findAllByStatus(PostStatus.ACTIVE, pageable);

        return posts.map(post ->{
            Long viewCount = communityPostLogService.getViewCount(post);
            LocalDateTime displayTime = editCheck(post);
            Long chatCount = communityCommentService.countByCommunityPostId(post.getId());

            return CommunityPostReadAllPostResponse.of(
                    post,
                    viewCount,
                    displayTime,
                    chatCount
            );
        });
    }
    // 3. 특정 게시글 조회
    @Transactional
    public CommunityPostReadPostResponse getPostId(String viewerId, Long postId, CommunityPostReadPostRequest request){
        CommunityPost post = communityPostRepo.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글 없음"));

        communityPostLogService.checkAndRecordView(viewerId, post);
        Long viewCount = communityPostLogService.getViewCount(post);

        List<CommunityCommentResponseDto> comments =
                communityCommentService.getCommentsByPostId(postId);
        LocalDateTime displayTime = editCheck(post);

        return CommunityPostReadPostResponse.of(
                post,
                comments,
                viewCount,
                displayTime
        );
    }
    // 4. 게시글 수정
    @Transactional
    public void updatePost(String authorId, Long postId, CommunityPostUpdatePostRequest request){
        CommunityPost post = communityPostRepo.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글 없음"));
        if(!post.getAuthorId().equals(authorId)){
            throw new UnauthorizedPostAccessException("작성자만 수정할 수 있습니다.");
        }

        post.updatePost(request.title(), request.content());

        communityPostRepo.save(post);
    }

    // 5. 게시글 삭제
    @Transactional
    public void deletePost(String authorId, Long id){
        CommunityPost post = communityPostRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("게시글 없음"));
        if(!post.getAuthorId().equals(authorId)){
            throw new UnauthorizedPostAccessException("작성자만 삭제할 수 있습니다.");
        }

        post.softDelete();
    }

    // 6. 수정된 게시물인지 판단
    @Transactional(readOnly = true)
    public LocalDateTime editCheck(CommunityPost post){
        return post.getUpdatedAt() != null ? post.getUpdatedAt() : post.getCreatedAt();
    }
}