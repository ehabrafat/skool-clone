package com.example.Skool.chat.messages;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {


    @Query("SELECT m FROM Message m WHERE m.conversation.id = :conversationId order by m.createdAt desc")
    Page<Message> getMessagesByConversationId(Integer conversationId, Pageable pageable);
}
