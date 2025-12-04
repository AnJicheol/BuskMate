package org.example.buskmate.community.service;

import lombok.RequiredArgsConstructor;
import org.example.buskmate.community.domain.CommunityComment;
import org.example.buskmate.community.domain.CommunityPost;
import org.example.buskmate.community.domain.DeleteStatus;
import org.example.buskmate.community.dto.post.crud.request.*;
import org.example.buskmate.community.dto.post.crud.response.CommunityPostReadAllPostResponse;
import org.example.buskmate.community.dto.post.crud.response.CommunityPostReadPostResponse;
import org.example.buskmate.community.repository.CommunityCommentRepository;
import org.example.buskmate.community.repository.CommunityPostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommunityPostServiceImpl implements CommunityPostService {

    private final CommunityPostRepository communityPostRepo;
    private final CommunityCommentRepository communityCommentRepo;

    // 1. 게시글 생성
    @Transactional
    public void createPost(CommunityPostCreatePostRequest request){
        CommunityPost post = CommunityPost.createPost(
                request.title(),
                request.authorId(),
                request.content()
        );
        communityPostRepo.save(post);
    }
    // 2. 전체 게시글 조회
    public Page<CommunityPostReadAllPostResponse> getAllPost(CommunityPostReadAllPostRequest request){
        Sort sort = request.desc() ? Sort.by(request.sortBy()).descending()
                                    : Sort.by(request.sortBy()).ascending();
        Pageable pageable = PageRequest.of(request.page(), request.size(), sort);

        return communityPostRepo.findByIsDeleted(DeleteStatus.ACTIVE, pageable)
                .map(post -> {
                    // 댓글 수 조회
                    long commentCount = communityCommentRepo.countByCommunityPostId(post.getId());
                    return new CommunityPostReadAllPostResponse(
                            post.getAuthorId(),
                            post.getTitle(),
                            post.getContent(),
                            post.getViewCount(),
                            commentCount
                    );
                });
    }
    // 3. 특정 게시글 조회
    @Transactional
    public CommunityPostReadPostResponse getPostId(Long id, CommunityPostReadPostRequest request){
        CommunityPost post = communityPostRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("게시글 없음"));
        post.increaseViewCount(); // 조회수 증가
        //댓글 조회
        List<CommunityComment> comments =
                communityCommentRepo.findByCommunityPostIdAndIsDeletedOrderByCreatedAtAsc(
                        post.getId(),
                        DeleteStatus.ACTIVE
                );
        return CommunityPostReadPostResponse.of(post, comments);
    }
    // 4. 게시글 수정
    @Transactional
    public void updatePost(Long id, CommunityPostUpdatePostRequest request){
        CommunityPost post = communityPostRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("게시글 없음"));

        post.updatePost(request.title(), request.content());
        communityPostRepo.save(post);
    }

    // 5. 게시글 삭제
    public void deletePost(Long id, CommunityPostDeletePostRequest request){
        CommunityPost post = communityPostRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("게시글 없음"));

        post.softDelete();
    }
}