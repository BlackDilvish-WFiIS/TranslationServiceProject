package com.project.cisco.unit.service;

import com.project.cisco.CiscoApplication;
import com.project.cisco.database.entity.Tag;
import com.project.cisco.database.repository.TagRepository;
import com.project.cisco.dto.TagDto;
import com.project.cisco.exception.NotFoundException;
import com.project.cisco.mapper.TagMapper;
import com.project.cisco.service.impl.TagServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest(classes = CiscoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TagServiceTest {
    @InjectMocks
    private TagServiceImpl tagService;

    @Mock
    private TagRepository tagRepository;

    @Mock
    private TagMapper tagMapper;

    private Tag tag;
    private Tag modifiedTag;
    private TagDto tagDto;
    private TagDto modifiedTagDto;

    @BeforeEach
    public void init(){
        tag = new Tag();
        tag.setTag("Info messages");
        tag.setId(1L);

        modifiedTag = new Tag();
        modifiedTag.setTag("Warning messages");
        modifiedTag.setId(1L);

        tagDto = TagDto.builder().id(1L).tag("Info messages").build();
        modifiedTagDto = TagDto.builder().id(1L).tag("Warning messages").build();
    }

    @Test
    public void shouldAddNewTag(){
        Mockito.when(tagMapper.map(tagDto)).thenReturn(tag);

        tagService.addTag(tagDto);

        Mockito.verify(tagRepository, Mockito.times(1)).save(tag);
    }

    @Test
    public void shouldGetExistingTag(){
        Mockito.when(tagRepository.findById(tag.getId())).thenReturn(Optional.of(tag));

        tagService.getTag(1L);

        Mockito.verify(tagRepository, Mockito.times(1)).findById(1L);
    }

    @Test
    public void shouldReturnNotFoundExceptionWhenAttemptToGetNotExistingTag(){
        Mockito.when(tagRepository.findById(tag.getId())).thenReturn(Optional.of(tag));

        Exception exception = assertThrows(NotFoundException.class, () -> {
            tagService.getTag(111111L);
        });

        String expectedMessage = "Tag with given id does not exist";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void shouldModifyExistingTag(){
        Mockito.when(tagRepository.findById(tag.getId())).thenReturn(Optional.of(tag));

        tagService.modifyTag(modifiedTagDto);

        Mockito.verify(tagRepository, Mockito.times(1)).save(modifiedTag);
    }

    @Test
    public void shouldDeleteExistingTag(){
        Mockito.when(tagRepository.findById(tag.getId())).thenReturn(Optional.of(tag));

        tagService.deleteTag(tag.getId());

        Mockito.verify(tagRepository, Mockito.times(1)).deleteById(tag.getId());
    }

    @Test
    public void  shouldReturnNotFoundExceptionWhenAttemptToDeleteNotExistingTag(){
        Mockito.when(tagRepository.findById(tag.getId())).thenReturn(Optional.of(tag));

        Exception exception = assertThrows(NotFoundException.class, () -> {
            tagService.deleteTag(111111L);
        });

        String expectedMessage = "Tag with given id does not exist";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}
