package org.example.buskmate.messenger.chat.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.buskmate.band.dto.CustomUser;
import org.example.buskmate.messenger.chat.dto.ChatMessageResponse;
import org.example.buskmate.messenger.chat.service.ChatUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(
        name = "Chat Messages",
        description = "채팅 메시지 조회"
)
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chat/rooms")
public class ChatMessageController {
    private final ChatUseCase chatUseCase;

    @Operation(
            summary = "채팅방 메시지 목록 조회",
            description = """
            인증 필요.

            - Path: /api/chat/rooms/{roomId}/messages
            - roomId: 채팅방 식별자(외부 노출용)
            - cursor: 커서(선택) — 페이징을 위한 기준 값 (정책에 따라 messageId 또는 내부 PK 등을 사용)
            - size: 조회 개수 (기본 30)

            응답:
            - List<ChatMessageResponse>

            참고:
            - 호출자는 해당 채팅방의 멤버여야 하며, 권한 검증은 유스케이스/서비스 계층에서 수행된다.
            """
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "조회 성공",
                    content = @Content(schema = @Schema(implementation = ChatMessageResponse.class))
            ),
            @ApiResponse(responseCode = "400", description = "요청 값 검증 실패", content = @Content),
            @ApiResponse(responseCode = "401", description = "인증 실패", content = @Content),
            @ApiResponse(responseCode = "403", description = "권한 없음(채팅방 멤버 아님)", content = @Content),
            @ApiResponse(responseCode = "404", description = "채팅방 없음", content = @Content)
    })
    @GetMapping("/{roomId}/messages")
    public ResponseEntity<List<ChatMessageResponse>> getMessages(
            @PathVariable String roomId,
            @RequestParam(required = false) String cursor,
            @RequestParam(defaultValue = "30") int size,
            @AuthenticationPrincipal CustomUser user
    ) {
        return ResponseEntity.ok(chatUseCase.getMessages(roomId, user.getUserId(), cursor, size));
    }

}
