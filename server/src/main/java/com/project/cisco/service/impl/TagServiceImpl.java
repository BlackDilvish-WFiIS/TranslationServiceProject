package com.project.cisco.service.impl;

import com.project.cisco.database.entity.Tag;
import com.project.cisco.database.repository.MessageRepository;
import com.project.cisco.database.repository.TagRepository;
import com.project.cisco.dto.TagDto;
import com.project.cisco.exception.LengthConstraintViolationException;
import com.project.cisco.exception.NotFoundException;
import com.project.cisco.exception.UniqueConstraintViolationException;
import com.project.cisco.exception.LengthConstraintViolationException;
import com.project.cisco.mapper.TagMapper;
import com.project.cisco.service.TagService;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionSystemException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TagServiceImpl implements TagService {
    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private TagMapper tagMapper;

    @Override
    public TagDto addTag(TagDto tagDto) {
        Tag tag = tagMapper.map(tagDto);
        try {
            Tag savedTag = tagRepository.save(tag);
            return tagMapper.map(savedTag);
        } catch (DataIntegrityViolationException e) {
            throw new UniqueConstraintViolationException("Tag with given tag name already exists!");
        } catch(TransactionSystemException e) {
            throw new LengthConstraintViolationException("Tag name must be between 2 and 32 characters long.");
        }
    }

    @Override
    public TagDto getTag(Long id) {
        Optional<Tag> tagOptional = tagRepository.findById(id);
        if (tagOptional.isEmpty()) {
            throw new NotFoundException("Tag with given id does not exist!");
        }
        return tagMapper.map(tagOptional.get());
    }

    @Override
    public List<TagDto> getTags() {
        List<Tag> tags = tagRepository.findAll();
        return tags.stream().map(tag -> tagMapper.map(tag)).toList();
    }

    @Override
    public void deleteTag(Long id) {
        Optional<Tag> tagOptional = tagRepository.findById(id);
        if (tagOptional.isEmpty()) {
            throw new NotFoundException("Tag with given id does not exist!");
        }
        tagOptional.get().getMessages()
                        .forEach(message -> {
                            List<Tag> updatedTags = message.getTags()
                                    .stream()
                                    .filter(tag -> !tag.equals(tagOptional.get()))
                                    .collect(Collectors.toList());
                            message.setTags(updatedTags);
                            messageRepository.save(message);
                        });
        tagOptional.get().setMessages(null);
        tagRepository.deleteById(id);
    }

    @Override
    public TagDto modifyTag(TagDto tagDto) {
        Optional<Tag> tagOptional = tagRepository.findById(tagDto.getId());
        if (tagOptional.isEmpty()) {
            throw new NotFoundException("Tag with given id does not exist!");
        }
        Tag tag = tagOptional.get();
        tag.setTag(tagDto.getTag());
        Tag modifiedTag = tagRepository.save(tag);
        return tagMapper.map(modifiedTag);
    }
}
