package com.jr_devs.assemblog.service.tag;

import com.jr_devs.assemblog.model.post.PostTag;

import java.util.List;

public interface PostTagService {

    void createPostTag(Long boardId, List<String> tags);

    void deletePostTag(Long postId, Long tagId);

    List<PostTag> getPostTagsByPostId(Long postId);

    List<PostTag> getPostTagsByTagId(Long tagId);
}
