package com.jr_devs.assemblog.service.tag;

import com.jr_devs.assemblog.model.dto.ResponseDto;
import com.jr_devs.assemblog.model.tag.Tag;
import com.jr_devs.assemblog.model.tag.TagDto;

import java.util.List;

public interface TagService {

    Tag createTag(TagDto tagDto);

    ResponseDto deleteTag(Long tagId);

    Tag readTagById(Long tagId);

    Tag readTagByName(String name);

    List<Tag> readAllTags();
}
