package com.project.cisco.unit.mapper;

import com.project.cisco.CiscoApplication;
import com.project.cisco.database.entity.Tag;
import com.project.cisco.dto.TagDto;
import com.project.cisco.mapper.TagMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = CiscoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TagMapperTest {
    @Autowired
    private TagMapper TagMapper;

    @Test
    public void shouldCorrectlyConvertTagToTagDto() {
        Tag tag = new Tag();
        tag.setId(1L);
        tag.setTag("General exception");

        TagDto expectedDto = TagDto.builder().id(1L).tag("General exception").build();

        TagDto resultDto = TagMapper.map(tag);

        Assertions.assertEquals(resultDto.getId(), expectedDto.getId());
        Assertions.assertEquals(resultDto.getTag(), expectedDto.getTag());
    }

    @Test
    public void shouldCorrectlyConvertTagDtoToTag() {
        Tag expectedTag = new Tag();
        expectedTag.setId(1L);
        expectedTag.setTag("Router service");

        TagDto tagDto = TagDto.builder().id(2L).tag("Router service").build();

        Tag result = TagMapper.map(tagDto);

        Assertions.assertEquals(expectedTag.getTag(), result.getTag());
    }
}