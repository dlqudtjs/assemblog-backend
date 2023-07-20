package com.jr_devs.assemblog.services.tag;

import com.jr_devs.assemblog.models.dto.ResponseDto;
import com.jr_devs.assemblog.models.tag.Tag;
import com.jr_devs.assemblog.models.tag.TagDto;

import java.util.List;

public interface TagService {

    Tag createTag(TagDto tagDto);

    ResponseDto deleteTag(Long tagId);

    Tag readTagById(Long tagId);

    Tag readTagByName(String name);

    List<Tag> readAllTags();
}
