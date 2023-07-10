package com.jr_devs.assemblog.services.post;

import com.jr_devs.assemblog.models.dtos.post.PostDto;
import com.jr_devs.assemblog.models.dtos.post.PostListResponseDto;
import com.jr_devs.assemblog.models.dtos.post.PostResponseDto;
import com.jr_devs.assemblog.models.dtos.ResponseDto;

public interface PostService {

    ResponseDto createPost(PostDto postDto);

    ResponseDto tempSavePost(PostDto postDto, String token);

    PostResponseDto readPost(Long postId);

    ResponseDto updatePost(PostDto postDto, String token);

    ResponseDto deletePost(Long postId, String token);

    PostListResponseDto readPostList(int pageStartIndex, int pageEndIndex, String order, String orderType, int searchType);
}
