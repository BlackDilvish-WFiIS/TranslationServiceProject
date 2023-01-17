package com.project.cisco.service;

import com.project.cisco.dto.MessageDto;

import java.util.List;

public interface MessageService {
    MessageDto addMessage(MessageDto messageDto);

    MessageDto getMessage(Long id);

    List<MessageDto> getMessages();

    void deleteMessage(Long id);

    MessageDto modifyMessage(MessageDto messageDto);
}
