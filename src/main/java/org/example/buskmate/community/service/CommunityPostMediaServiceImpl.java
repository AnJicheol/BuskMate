package org.example.buskmate.community.service;

import lombok.RequiredArgsConstructor;
import org.example.buskmate.community.domain.CommunityPost;
import org.example.buskmate.community.domain.CommunityPostData;
import org.example.buskmate.community.dto.PostBlockRequestDto;
import org.example.buskmate.community.dto.PostBlockResponseDto;
import org.example.buskmate.community.repository.CommunityPostDataRepository;
import org.example.buskmate.community.repository.CommunityPostRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommunityPostMediaServiceImpl implements  CommunityPostMediaService {

    private final CommunityPostRepository communityPostRepository;
    private final CommunityPostDataRepository communityPostDataRepository;

    @Override
    public List<PostBlockResponseDto> getBlocksByPostId(Long postId) {
        CommunityPost post = communityPostRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다. id=" + postId));

        return communityPostDataRepository.findByCommunityPost_IdOrderBySortOrderAsc(post.getId())
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public Long addBlock(Long postId, PostBlockRequestDto request) {
        CommunityPost post = communityPostRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다. id=" + postId));

        CommunityPostData block = CommunityPostData.builder()
                .communityPost(post)
                .mediaType(request.getMediaType())
                .content(request.getContent())
                .sortOrder(request.getSortOrder())
                .build();

        CommunityPostData saved = communityPostDataRepository.save(block);
        return saved.getId();
    }

    @Override
    @Transactional
    public void updateBlock(Long blockId, PostBlockRequestDto request) {
        CommunityPostData block = communityPostDataRepository.findById(blockId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 블록입니다. id=" + blockId));

        // 타입/내용/순서 모두 수정 가능하게
        block.updateMediaType(request.getMediaType());
        block.updateContent(request.getContent());
        block.updateSortOrder(request.getSortOrder());
    }

    @Override
    @Transactional
    public void deleteBlock(Long blockId) {
        CommunityPostData block = communityPostDataRepository.findById(blockId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 블록입니다. id=" + blockId));


        communityPostDataRepository.delete(block);
    }

    private PostBlockResponseDto toResponse(CommunityPostData entity) {
        return PostBlockResponseDto.builder()
                .id(entity.getId())
                .mediaType(entity.getMediaType())
                .content(entity.getContent())
                .sortOrder(entity.getSortOrder())
                .build();
    }
}
