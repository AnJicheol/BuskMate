package org.example.buskmate.messenger.chat.controller;

import lombok.RequiredArgsConstructor;
import org.example.buskmate.messenger.chat.dto.ChatMessageResponse;
import org.example.buskmate.messenger.chat.dto.ChatSendRequest;
import org.example.buskmate.messenger.chat.service.ChatUseCase;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@RequiredArgsConstructor
@Controller
public class ChatSocketController {

    private final ChatUseCase chatUseCase;
    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/room/{roomId}/send")
    public void send(@DestinationVariable String roomId,
                     ChatSendRequest request,
                     Principal principal) {

        String senderId = principal.getName();
        ChatMessageResponse saved = chatUseCase.sendMessage(roomId, senderId, request.content());

        messagingTemplate.convertAndSend("/chat/room/" + roomId, saved);
    }
}
