package com.jr_devs.assemblog.services.tag;

import com.jr_devs.assemblog.models.dtos.ResponseDto;
import com.jr_devs.assemblog.models.Tag;
import com.jr_devs.assemblog.models.dtos.TagDto;

import java.util.List;

public interface TagService {

    Tag createTag(TagDto tagDto);

    ResponseDto deleteTag(Long tagId);

    Tag readTagById(Long tagId);

    Tag readTagByName(String name);

    List<Tag> readAllTags();
}
