package com.project.cisco.api;

import com.project.cisco.dto.MessageDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface MessageApi {
    @GetMapping(value = "/message/{id}")
    ResponseEntity<MessageDto> getMessage(@PathVariable(value = "id") Long messageId);

    @GetMapping(value = "/message")
    ResponseEntity<List<MessageDto>> getMessages();

    @PostMapping(value = "/message")
    ResponseEntity<MessageDto> addMessage(@RequestBody MessageDto messageDto);

    @DeleteMapping(value = "/message/{id}")
    ResponseEntity<Void> deleteMessage(@PathVariable(value = "id") Long messageId);

    @PutMapping(value = "/message")
    ResponseEntity<MessageDto> modifyMessage(@RequestBody MessageDto messageDto);
}
