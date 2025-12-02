package org.example.buskmate.community.service;

import lombok.RequiredArgsConstructor;
import org.example.buskmate.community.dto.crud.request.CreatePostRequest;
import org.example.buskmate.community.dto.crud.request.DeletePostRequest;
import org.example.buskmate.community.dto.crud.request.UpdatePostRequest;
import org.example.buskmate.community.dto.crud.response.CreatePostResponse;
import org.example.buskmate.community.dto.crud.response.DeletePostResponse;
import org.example.buskmate.community.dto.crud.response.PostIdResponse;
import org.example.buskmate.community.dto.crud.response.UpdatePostResponse;
import org.example.buskmate.community.repository.CommunityPostRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommunityPostServiceImpl implements CommunityPostService {

    private final CommunityPostRepository communityPostRepo;

    public CreatePostResponse createPost(CreatePostRequest request){
        return null;
    }

    public PostIdResponse getPostById(String id){
        return null;
    }

    public UpdatePostResponse updatePost(UpdatePostRequest request){
        return null;
    }

    public DeletePostResponse deletePost(DeletePostRequest request){
        return null;
    }
}
