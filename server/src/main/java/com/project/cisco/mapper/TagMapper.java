package com.project.cisco.mapper;

import com.project.cisco.database.entity.Tag;
import com.project.cisco.dto.TagDto;
import org.springframework.stereotype.Component;

@Component
public class TagMapper {
    public Tag map(TagDto tagDto) {
        return new Tag(tagDto.getId(), tagDto.getTag());
    }

    public TagDto map(Tag tag) {
        return TagDto.builder().id(tag.getId()).tag(tag.getTag()).build();
    }
}
