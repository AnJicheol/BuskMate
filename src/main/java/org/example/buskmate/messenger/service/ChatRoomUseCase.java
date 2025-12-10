package org.example.buskmate.messenger.service;

import lombok.RequiredArgsConstructor;
import org.example.buskmate.messenger.domain.ChatRoom;
import org.example.buskmate.messenger.dto.CustomUser;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class ChatRoomUseCase {
    private final ChatRoomService chatRoomService;
    private final ChatMemberService chatMemberService;

    @Transactional
    public void createGroupChatRoom(String roomTitle, CustomUser owner) {
        ChatRoom chatRoom = chatRoomService.createChatRoom(roomTitle);
        chatMemberService.addMemberToChatRoom(chatRoom, owner);
    }

}
