package org.example.buskmate.messenger.service;


import com.github.f4b6a3.ulid.UlidCreator;
import lombok.RequiredArgsConstructor;
import org.example.buskmate.messenger.domain.ChatRoom;
import org.example.buskmate.messenger.repository.ChatRoomRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ChatRoomServiceImpl implements ChatRoomService{
    private final ChatRoomRepository chatRoomRepository;

    @Transactional
    public ChatRoom createChatRoom(String roomTitle) {
        String roomId = UlidCreator.getUlid().toString();

        ChatRoom chatRoom = new ChatRoom(roomId, roomTitle);
        chatRoomRepository.save(chatRoom);
        return chatRoom;
    }
}
