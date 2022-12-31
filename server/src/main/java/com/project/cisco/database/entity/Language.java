package com.project.cisco.database.entity;

import jakarta.persistence.*;
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
public class Language {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column
    private String language;
}
