package org.example.buskmate.community.service;

import org.example.buskmate.community.dto.crud.request.CreatePostRequest;
import org.example.buskmate.community.dto.crud.request.DeletePostRequest;
import org.example.buskmate.community.dto.crud.request.UpdatePostRequest;
import org.example.buskmate.community.dto.crud.response.CreatePostResponse;
import org.example.buskmate.community.dto.crud.response.DeletePostResponse;
import org.example.buskmate.community.dto.crud.response.PostIdResponse;
import org.example.buskmate.community.dto.crud.response.UpdatePostResponse;

public interface CommunityPostService {

    CreatePostResponse createPost(CreatePostRequest request);

    PostIdResponse getPostById(String id);

    UpdatePostResponse updatePost(UpdatePostRequest request);

    DeletePostResponse deletePost(DeletePostRequest request);
}
