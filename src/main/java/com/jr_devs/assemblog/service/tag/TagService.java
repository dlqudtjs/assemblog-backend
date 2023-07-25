package com.jr_devs.assemblog.service.tag;

import com.jr_devs.assemblog.model.dto.ResponseDto;
import com.jr_devs.assemblog.model.tag.Tag;

import java.util.List;

public interface TagService {

    ResponseDto createTag(List<String> tags);

    ResponseDto deleteTag(Long tagId);

    Tag readTagById(Long tagId);

    Tag readTagByName(String name);

    List<Tag> readAllTags();
}
