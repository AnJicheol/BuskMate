package org.example.buskmate.messenger.repository;

import org.example.buskmate.messenger.domain.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
}
