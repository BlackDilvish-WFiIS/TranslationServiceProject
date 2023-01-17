package com.project.cisco.controler;

import com.project.cisco.api.MessageApi;
import com.project.cisco.dto.MessageDto;
import com.project.cisco.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(value = { "http://localhost:3000/" },
        maxAge = 900
)
@RestController
@RequestMapping("/v1")
public class MessageApiController implements MessageApi {

    @Autowired
    private MessageService messageService;

    @Override
    public ResponseEntity<MessageDto> getMessage(Long messageId) {
        MessageDto messageDto = messageService.getMessage(messageId);
        return ResponseEntity.ok(messageDto);
    }

    @Override
    public ResponseEntity<List<MessageDto>> getMessages() {
        List<MessageDto> messageDtos = messageService.getMessages();
        return ResponseEntity.ok(messageDtos);
    }

    @Override
    public ResponseEntity<MessageDto> addMessage(MessageDto messageDto) {
        MessageDto savedDto = messageService.addMessage(messageDto);
        return ResponseEntity.ok(savedDto);
    }

    @Override
    public ResponseEntity<Void> deleteMessage(Long messageId) {
        messageService.deleteMessage(messageId);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<MessageDto> modifyMessage(MessageDto messageDto) {
        MessageDto modifiedMessageDto = messageService.modifyMessage(messageDto);
        return ResponseEntity.ok(modifiedMessageDto);
    }
}