package com.project.cisco.mapper;

import com.project.cisco.database.entity.Message;
import com.project.cisco.database.entity.Tag;
import com.project.cisco.database.repository.LanguageRepository;
import com.project.cisco.database.repository.MessageRepository;
import com.project.cisco.database.repository.TagRepository;
import com.project.cisco.dto.MessageDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class MessageMapper {
    @Autowired
    private LanguageRepository languageRepository;
    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private MessageRepository messageRepository;

    public Message map(MessageDto messageDto) {
        Message mappedMessage = new Message();
        mappedMessage.setLanguage(languageRepository.findByLanguage(messageDto.getLanguage()));
        mappedMessage.setContent(messageDto.getContent());
        List<Tag> tags = new ArrayList<>();
        if (messageDto.getTags() != null) {
            for (Optional<String> tagName : messageDto.getTags()) {
                tags.add(tagRepository.findByTag(tagName.get()));
            }
        }
        mappedMessage.setTags(tags);
        mappedMessage.setOriginal_message(messageDto.getOriginal_message() != null ? messageRepository.findById(messageDto.getOriginal_message()).get() : null);

        return mappedMessage;
    }

    public MessageDto map(Message message) {
        List<Tag> tags = message.getTags();
        List<Optional<String>> tagsDto = new ArrayList<>();
        for (Tag tag : tags) {
            tagsDto.add(Optional.ofNullable(tag.getTag()));
        }
        return MessageDto.builder().id(message.getId()).original_message(message.getOriginal_message() != null ? message.getOriginal_message().getId() : null)
                .language(message.getLanguage().getLanguage())
                .content(message.getContent())
                .tags(tagsDto)
                .build();
    }
}
