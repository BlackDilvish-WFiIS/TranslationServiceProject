package com.project.cisco.unit.mapper;

import com.project.cisco.CiscoApplication;
import com.project.cisco.database.entity.Language;
import com.project.cisco.database.entity.Message;
import com.project.cisco.database.entity.Tag;
import com.project.cisco.dto.MessageDto;
import com.project.cisco.mapper.MessageMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

@SpringBootTest(classes = CiscoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MessageMapperTest {
    private static final String TEST_LANGUAGE = "English";
    private static final String TEST_CONTENT = "Test message";
    private static final String TEST_TAG = "Test tag";

    @Autowired
    private MessageMapper messageMapper;

    private Message message;
    private MessageDto messageDto;

    @BeforeEach
    public void init() {
        message = new Message();
        message.setId(1L);
        message.setOriginal_message(null);
        message.setLanguage(new Language(TEST_LANGUAGE));
        message.setContent(TEST_CONTENT);
        message.setTags(List.of(new Tag(TEST_TAG)));

        messageDto = MessageDto.builder().id(1L).original_message(null).language(TEST_LANGUAGE).content(TEST_CONTENT).tags(List.of(Optional.of(TEST_TAG))).build();
    }

    @Test
    public void shouldCorrectlyConvertMessageToMessageDto() {
        MessageDto expectedDto = messageDto;
        MessageDto resultDto = messageMapper.map(message);

        Assertions.assertEquals(resultDto.getId(), expectedDto.getId());
        Assertions.assertEquals(resultDto.getOriginal_message(), expectedDto.getOriginal_message());
        Assertions.assertEquals(resultDto.getLanguage(), expectedDto.getLanguage());
        Assertions.assertEquals(resultDto.getContent(), expectedDto.getContent());
        Assertions.assertEquals(resultDto.getTags().get(0), expectedDto.getTags().get(0));
    }

    @Test
    public void shouldCorrectlyConvertMessageDtoToMessage() {
        Message expectedMessage = message;
        Message result = messageMapper.map(messageDto);

        Assertions.assertEquals(result.getOriginal_message(), expectedMessage.getOriginal_message());
        Assertions.assertEquals(result.getContent(), expectedMessage.getContent());
    }
}
