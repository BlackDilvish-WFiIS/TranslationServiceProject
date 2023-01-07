package com.project.cisco.unit.service;

import com.project.cisco.CiscoApplication;
import com.project.cisco.database.entity.Language;
import com.project.cisco.database.entity.Message;
import com.project.cisco.database.entity.Tag;
import com.project.cisco.database.repository.LanguageRepository;
import com.project.cisco.database.repository.MessageRepository;
import com.project.cisco.database.repository.TagRepository;
import com.project.cisco.dto.MessageDto;
import com.project.cisco.exception.NotAllowedLanguageException;
import com.project.cisco.mapper.MessageMapper;
import com.project.cisco.service.impl.MessageServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(classes = CiscoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class MessageServiceTest {
    private static final String TEST_LANGUAGE = "English";
    private static final String TEST_CONTENT = "Test message";
    private static final String TEST_TAG = "Test tag";
    @InjectMocks
    private MessageServiceImpl messageService;

    @Mock
    private MessageRepository messageRepository;

    @Mock
    private MessageMapper messageMapper;

    @Mock
    private LanguageRepository languageRepository;
    @Mock
    private TagRepository tagRepository;


    private Message message;
    private Message modifiedMessage;
    private MessageDto messageDto;
    private MessageDto modifiedMessageDto;
    private MessageDto invalidOriginalMessageDto;


    @BeforeEach
    public void init() {
        message = new Message();
        message.setId(1L);
        message.setOriginal_message(null);
        message.setLanguage(new Language(TEST_LANGUAGE));
        message.setContent(TEST_CONTENT);
        message.setTags(List.of(new Tag(TEST_TAG)));

        modifiedMessage = new Message();
        modifiedMessage.setId(1L);
        modifiedMessage.setOriginal_message(null);
        modifiedMessage.setLanguage(new Language(TEST_LANGUAGE));
        modifiedMessage.setContent("TEST_CONTENT");
        modifiedMessage.setTags(List.of(new Tag(TEST_TAG)));

        messageDto = MessageDto.builder().id(1L).original_message(null).language(TEST_LANGUAGE).content(TEST_CONTENT).tags(List.of(Optional.of(TEST_TAG))).build();
        modifiedMessageDto = MessageDto.builder().id(1L).original_message(null).language(TEST_LANGUAGE).content("TEST_CONTENT").tags(List.of(Optional.of(TEST_TAG))).build();
        invalidOriginalMessageDto = MessageDto.builder().id(1L).original_message(null).language("Polish").content("TEST_CONTENT").tags(List.of(Optional.of(TEST_TAG))).build();
    }

    @Test
    public void shouldAddNewMessage() {
        Mockito.when(messageMapper.map(messageDto)).thenReturn(message);

        messageService.addMessage(messageDto);

        Mockito.verify(messageRepository, Mockito.times(1)).save(message);
    }


    @Test
    public void shouldThrowExceptionWhileAddingNewMessage() {
        NotAllowedLanguageException exception = assertThrows(NotAllowedLanguageException.class, () -> messageService.addMessage(invalidOriginalMessageDto));
        assertEquals("Original message can be only in english", exception.getMessage());
    }

    @Test
    public void shouldGetExistingMessage() {
        Mockito.when(messageRepository.findById(message.getId())).thenReturn(Optional.of(message));

        messageService.getMessage(1L);

        Mockito.verify(messageRepository, Mockito.times(1)).findById(1L);
    }

    @Test
    public void shouldModifyExistingMessage() {
        Mockito.when(messageRepository.findById(message.getId())).thenReturn(Optional.of(message));
        Mockito.when(messageMapper.map(modifiedMessageDto)).thenReturn(modifiedMessage);

        messageService.modifyMessage(modifiedMessageDto);

        Mockito.verify(messageRepository, Mockito.times(1)).save(modifiedMessage);
    }

    @Test
    public void shouldDeleteExistingMessage() {
        Mockito.when(messageRepository.findById(message.getId())).thenReturn(Optional.of(message));

        messageService.deleteMessage(message.getId());

        Mockito.verify(messageRepository, Mockito.times(1)).deleteById(message.getId());
    }
}

