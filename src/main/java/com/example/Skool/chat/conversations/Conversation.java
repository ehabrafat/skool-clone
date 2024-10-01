package com.example.Skool.chat.conversations;

import com.example.Skool.auth.entities.UserCreator;
import com.example.Skool.chat.messages.Message;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "conversations", uniqueConstraints = {@UniqueConstraint(columnNames = {"member_one_id", "member_two_id"})})
@Data
public class Conversation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    private UserCreator memberOne;

    @ManyToOne(fetch = FetchType.EAGER)
    private UserCreator memberTwo;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Message> messages;
}
