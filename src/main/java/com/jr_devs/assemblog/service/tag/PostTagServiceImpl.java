package com.jr_devs.assemblog.service.tag;


import com.jr_devs.assemblog.model.post.PostTag;
import com.jr_devs.assemblog.model.tag.Tag;
import com.jr_devs.assemblog.repository.JpaPostTagRepository;
import com.jr_devs.assemblog.repository.JpaTagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostTagServiceImpl implements PostTagService {

    private final JpaPostTagRepository postTagRepository;
    private final JpaTagRepository tagRepository;

    @Override
    @Transactional
    public void createPostTag(Long boardId, List<String> tags) {
        for (String tagName : tags) {
            Tag tag = tagRepository.findByName(tagName);
            PostTag postTag = postTagRepository.findByPostIdAndTagId(boardId, tag.getId());

            if (postTag != null) {
                continue;
            }

            postTagRepository.save(PostTag.builder()
                    .postId(boardId)
                    .tagId(tag.getId())
                    .build());
        }
    }

    @Override
    public void deletePostTag(Long postId, Long tagId) {
        PostTag postTag = postTagRepository.findByPostIdAndTagId(postId, tagId);

        if (postTag == null) {
            return;
        }

        postTagRepository.deleteById(postTag.getId());
    }

    @Override
    public List<PostTag> getPostTagsByPostId(Long postId) {
        return postTagRepository.findAllByPostId(postId);
    }

    @Override
    public List<PostTag> getPostTagsByTagId(Long tagId) {
        return postTagRepository.findAllByTagId(tagId);
    }
}
