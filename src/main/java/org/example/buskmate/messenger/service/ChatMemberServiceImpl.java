package org.example.buskmate.messenger.service;

import lombok.RequiredArgsConstructor;
import org.example.buskmate.messenger.domain.ChatRoom;
import org.example.buskmate.messenger.domain.ChatRoomMember;
import org.example.buskmate.messenger.domain.ChatRoomRole;
import org.example.buskmate.messenger.dto.CustomUser;
import org.example.buskmate.messenger.repository.ChatMemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ChatMemberServiceImpl implements ChatMemberService {
    private final ChatMemberRepository chatMemberRepository;

    @Transactional
    public void addMemberToChatRoom(ChatRoom chatRoom, CustomUser owner) {
        ChatRoomMember chatRoomMember = new ChatRoomMember(chatRoom, owner.getUserId(), ChatRoomRole.OWNER);
        chatMemberRepository.save(chatRoomMember);
    }


}
