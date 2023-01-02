package com.project.cisco.database.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.FieldNameConstants;

@Data
@ToString(callSuper = true)
@FieldNameConstants
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "language",
        uniqueConstraints = {@UniqueConstraint(columnNames = "language")})
public class Language {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column
    @NotBlank
    @Size(min = 2, max = 32, message = "Language name must be between 2 and 32 characters long")
    private String language;
}
