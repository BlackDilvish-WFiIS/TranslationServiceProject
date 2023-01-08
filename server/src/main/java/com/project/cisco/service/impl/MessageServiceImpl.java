package com.project.cisco.service.impl;

import com.project.cisco.database.entity.Language;
import com.project.cisco.database.entity.Message;
import com.project.cisco.database.entity.Tag;
import com.project.cisco.database.repository.LanguageRepository;
import com.project.cisco.database.repository.MessageRepository;
import com.project.cisco.database.repository.TagRepository;
import com.project.cisco.dto.MessageDto;
import com.project.cisco.exception.InvalidTagsException;
import com.project.cisco.exception.NotAllowedLanguageException;
import com.project.cisco.exception.NotFoundException;
import com.project.cisco.exception.UniqueConstraintViolationException;
import com.project.cisco.mapper.MessageMapper;
import com.project.cisco.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private LanguageRepository languageRepository;
    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private MessageMapper messageMapper;

    @Override
    public MessageDto addMessage(MessageDto messageDto) {
        try {
            if (messageDto.getOriginal_message() == null && !Objects.equals(messageDto.getLanguage(), "English")) {
                throw new NotAllowedLanguageException("Original message can be only in english");
            }
            if(messageDto.getOriginal_message() != null && !messageDto.getTags().isEmpty()){
                Optional<Message> original_message = messageRepository.findById(messageDto.getOriginal_message());
                MessageDto original_messageDto = messageMapper.map(original_message.get());
                if(original_messageDto.getTags().size() != messageDto.getTags().size() || !original_messageDto.getTags().containsAll(messageDto.getTags()) || !messageDto.getTags().containsAll(original_messageDto.getTags())){
                    throw new InvalidTagsException("Message has to have same tags as original message");
                }
            }
            if (languageRepository.findByLanguage(messageDto.getLanguage()) == null) {
                Language language = new Language(messageDto.getLanguage());
                Language savedLanguage = languageRepository.save(language);
            }
            if(messageDto.getTags() != null) {
                for (Optional<String> tagName : messageDto.getTags()) {
                    if (tagRepository.findByTag(tagName.get()) == null) {
                        Tag tag = new Tag(tagName.get());
                        Tag savedTag = tagRepository.save(tag);
                    }
                }
            }
            Message message = messageMapper.map(messageDto);
            Message savedMessage = messageRepository.save(message);
            return messageMapper.map(savedMessage);
        } catch (DataIntegrityViolationException e) {
            throw new UniqueConstraintViolationException("Message with given message already exists.");
        }
    }

    @Override
    public MessageDto getMessage(Long id) {
        Optional<Message> messageOptional = messageRepository.findById(id);
        if (messageOptional.isEmpty()) {
            throw new NotFoundException("Message with given id does not exist");
        }
        return messageMapper.map(messageOptional.get());
    }

    @Override
    public List<MessageDto> getMessages() {
        List<Message> messages = messageRepository.findAll();
        return messages.stream().map(message -> messageMapper.map(message)).toList();
    }

    @Override
    public void deleteMessage(Long id) {
        Optional<Message> messageOptional = messageRepository.findById(id);
        if (messageOptional.isEmpty()) {
            throw new NotFoundException("Message with given id does not exist");
        }
        messageRepository.deleteById(id);
    }

    @Override
    public MessageDto modifyMessage(MessageDto messageDto) {
        Optional<Message> messageOptional = messageRepository.findById(messageDto.getId());
        if (messageOptional.isEmpty()) {
            throw new NotFoundException("Message with given id does not exist");
        }
        if(messageOptional.get().getOriginal_message() == null && messageDto.getOriginal_message() != null){
            throw new InvalidTagsException("It is not allowed to change original message");
        }
        if (messageDto.getOriginal_message() == null && !Objects.equals(messageDto.getLanguage(), "English")) {
            throw new NotAllowedLanguageException("Original message can be only in english");
        }
        if(messageDto.getOriginal_message() != null && !messageDto.getTags().isEmpty()){
            Optional<Message> original_message = messageRepository.findById(messageDto.getOriginal_message());
            MessageDto original_messageDto = messageMapper.map(original_message.get());
            if(original_messageDto.getTags().size() != messageDto.getTags().size() || !original_messageDto.getTags().containsAll(messageDto.getTags()) || !messageDto.getTags().containsAll(original_messageDto.getTags())){
                throw new InvalidTagsException("Message has to have same tags as original message");
            }
        }
        if (languageRepository.findByLanguage(messageDto.getLanguage()) == null) {
            Language language = new Language(messageDto.getLanguage());
            Language savedLanguage = languageRepository.save(language);
        }
        if(messageDto.getTags() != null) {
            for (Optional<String> tagName : messageDto.getTags()) {
                if (tagRepository.findByTag(tagName.get()) == null) {
                    Tag tag = new Tag(tagName.get());
                    Tag savedTag = tagRepository.save(tag);
                }
            }
        }
        Message message = messageOptional.get();
        Message tempMessage = messageMapper.map(messageDto);
        message.setOriginal_message(tempMessage.getOriginal_message());
        message.setLanguage(tempMessage.getLanguage());
        message.setContent(tempMessage.getContent());
        message.setTags(tempMessage.getTags());
        Message modifiedMessage = messageRepository.save(message);
        return messageMapper.map(modifiedMessage);
    }
}