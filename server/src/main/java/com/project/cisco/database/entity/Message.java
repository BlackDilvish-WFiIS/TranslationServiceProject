package com.project.cisco.database.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldNameConstants;

import java.util.List;

@Data
@ToString(callSuper = true)
@FieldNameConstants
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "message")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional=true)
    @JoinColumn(name = "message_id")
    private Message original_message;

    @OneToMany(mappedBy = "original_message", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval=true)
    private List<Message> message;

    @ManyToOne
    @JoinColumn(name="language_id")
    private Language language;

    @Column(nullable = false)
    private String content;

    @ManyToMany(cascade = CascadeType.DETACH)
    @JoinTable(name = "tag_message",
            joinColumns = @JoinColumn(name = "message_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id",
                    referencedColumnName = "id"))
    private List<Tag> tags;
}