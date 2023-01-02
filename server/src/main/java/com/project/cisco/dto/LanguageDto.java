package com.project.cisco.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class LanguageDto {
    private Long id;
    private String language;
}
