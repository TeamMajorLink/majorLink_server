package com.example.majorLink.repository;

import com.example.majorLink.domain.mapping.UserChat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserChatRepository extends JpaRepository<UserChat, Long> {
    List<UserChat> findByChatRoomId(Long roomId);
}
