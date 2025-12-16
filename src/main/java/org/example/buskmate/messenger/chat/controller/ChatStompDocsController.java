package org.example.buskmate.messenger.chat.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Chat - STOMP", description = "WebSocket(STOMP) 채팅 프로토콜 안내")
@RestController
public class ChatStompDocsController {

    @Operation(
            summary = "STOMP 채팅 프로토콜",
            description = """
        연결 엔드포인트: /ws

        [SUBSCRIBE]
        - /chat/room/{roomId}

        [SEND]
        - /chat/cmd/room/{roomId}/send
        payload(JSON):
        { "content": "hello" }

        [BROADCAST payload]
        ChatMessageResponse가 그대로 내려옵니다.
        """
    )
    @GetMapping("/api/docs/chat-stomp")
    public void docs() { }
}
