package com.project.cisco.database.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.FieldNameConstants;

import java.util.List;

@Data
@ToString(callSuper = true)
@FieldNameConstants
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tag",
        uniqueConstraints = {@UniqueConstraint(columnNames = "tag")})
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column
    @NotBlank
    @Size(min = 2, max = 32, message = "Tag must be between 2 and 32 characters long")
    private String tag;

    @ManyToMany(mappedBy = "tags")
    private List<Message> messages;

    public Tag(String tag) {
        this.tag = tag;
    }

    public Tag(Long id, String tag) {
        this.id = id;
        this.tag = tag;
    }
}
