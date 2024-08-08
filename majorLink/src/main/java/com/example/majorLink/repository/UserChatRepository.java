package com.example.majorLink.repository;

import com.example.majorLink.domain.mapping.UserChat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserChatRepository extends JpaRepository<UserChat, Long> {
}
