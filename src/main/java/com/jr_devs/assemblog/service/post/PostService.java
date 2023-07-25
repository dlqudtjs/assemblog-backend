package com.jr_devs.assemblog.service.post;

import com.jr_devs.assemblog.model.post.PostRequest;
import com.jr_devs.assemblog.model.post.PostListResponse;
import com.jr_devs.assemblog.model.post.PostResponse;
import com.jr_devs.assemblog.model.dto.ResponseDto;

public interface PostService {

    ResponseDto createPost(PostRequest poseRequest);

    ResponseDto tempSavePost(PostRequest poseRequest, String token);

    ResponseDto readPost(Long postId);

    ResponseDto updatePost(PostRequest poseRequest, String token);

    ResponseDto deletePost(Long postId, String token);

    ResponseDto readPostList(int pageStartIndex, int pageEndIndex, String order, String orderType, String boardTitle, String tagName);

    void countView(Long postId);
}
