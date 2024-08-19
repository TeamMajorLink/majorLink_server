package com.example.majorLink.repository;

import com.example.majorLink.domain.Notification;
import com.example.majorLink.domain.User;
import com.example.majorLink.dto.response.NotificationResponse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findAllByReceiver(User user);
}
