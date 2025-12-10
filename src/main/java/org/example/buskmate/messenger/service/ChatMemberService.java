package org.example.buskmate.messenger.service;

import org.example.buskmate.messenger.domain.ChatRoom;
import org.example.buskmate.messenger.dto.CustomUser;

public interface ChatMemberService {
    void addMemberToChatRoom(ChatRoom chatRoom, CustomUser owner);
}
