package org.example.buskmate.messenger.Controller;


import lombok.RequiredArgsConstructor;
import org.example.buskmate.messenger.domain.ChatRoom;
import org.example.buskmate.messenger.service.ChatRoomUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chat/rooms")
public class ChatRoomController {
    private final ChatRoomUseCase chatRoomUseCase;

     @PostMapping
     public ResponseEntity<Void> createGroupChatRoom(@RequestBody ChatRoom chatRoom) {
         return ResponseEntity.ok(createdChatRoom);
     }

}
