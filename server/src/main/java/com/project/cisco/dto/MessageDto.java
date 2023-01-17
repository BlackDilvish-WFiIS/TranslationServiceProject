package com.project.cisco.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Optional;

@Builder
@Data
@AllArgsConstructor
public class MessageDto {
    private Long id;
    private Long original_message;
    private String language;
    private String content;
    private List<Optional<String>> tags;
}
