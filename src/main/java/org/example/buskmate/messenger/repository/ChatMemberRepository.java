package org.example.buskmate.messenger.repository;

import org.example.buskmate.messenger.domain.ChatRoomMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatMemberRepository extends JpaRepository<ChatRoomMember, Long> {
}
