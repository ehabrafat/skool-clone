package com.example.Skool.chat.conversations;

import jakarta.persistence.Tuple;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConversationRepository extends JpaRepository<Conversation, Integer> {

    Optional<Conversation> getByMemberOneIdAndMemberTwoId(int memberOneId, int memberTwoId);

    @Query("SELECT c FROM Conversation c WHERE c.memberOne.id = :userId OR c.memberTwo.id = :userId")
    Page<Conversation> getConversations(int userId, Pageable pageable);
}
