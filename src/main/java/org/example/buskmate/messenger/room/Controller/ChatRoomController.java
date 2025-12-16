package org.example.buskmate.messenger.room.Controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.buskmate.messenger.room.dto.*;
import org.example.buskmate.messenger.room.service.ChatRoomUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(
        name = "Chat Rooms",
        description = "채팅방 생성/삭제 및 멤버 초대/강퇴, 내 채팅방 목록 조회"
)
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chat/rooms")
public class ChatRoomController {
    private final ChatRoomUseCase chatRoomUseCase;

    @Operation(
            summary = "그룹 채팅방 생성",
            description = """
            인증 필요.

            - 요청 바디: RoomCreateRequest
            - 동작: 채팅방 생성 및 생성자를 OWNER로 등록
            """
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "생성 성공"),
            @ApiResponse(responseCode = "400", description = "요청 값 검증 실패", content = @Content),
            @ApiResponse(responseCode = "401", description = "인증 실패", content = @Content),
            @ApiResponse(responseCode = "403", description = "권한 없음", content = @Content)
    })
    @PostMapping
    public ResponseEntity<Void> createGroupChatRoom(@RequestBody RoomCreateRequest request, @AuthenticationPrincipal CustomUser owner) {
        chatRoomUseCase.createGroupChatRoom(request, owner);
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "멤버 초대",
            description = """
            인증 필요. (일반적으로 OWNER 권한 필요)

            - Path: /api/chat/rooms/{roomId}/members
            - roomId: 채팅방 식별자(ULID 등)
            - 요청 바디: MemberInviteRequest
            - 동작: 대상 사용자를 채팅방 멤버로 추가
            """
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "초대 성공"),
            @ApiResponse(responseCode = "400", description = "요청 값 검증 실패", content = @Content),
            @ApiResponse(responseCode = "401", description = "인증 실패", content = @Content),
            @ApiResponse(responseCode = "403", description = "권한 없음", content = @Content),
            @ApiResponse(responseCode = "404", description = "채팅방 또는 대상 사용자 없음", content = @Content)
    })
    @PostMapping("/{roomId}/members")
    public ResponseEntity<Void> inviteMember(
            @PathVariable String roomId,
            @Valid @RequestBody MemberInviteRequest request,
            @AuthenticationPrincipal CustomUser owner) {

        chatRoomUseCase.inviteMember(roomId, request, owner);
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "멤버 강퇴",
            description = """
            인증 필요. (일반적으로 OWNER 권한 필요)

            - Path: /api/chat/rooms/{roomId}/members/kick
            - roomId: 채팅방 식별자(ULID 등)
            - 요청 바디: MemberKickRequest
            - 동작: 대상 멤버를 채팅방에서 제거(강퇴)
            """
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "강퇴 성공"),
            @ApiResponse(responseCode = "400", description = "요청 값 검증 실패", content = @Content),
            @ApiResponse(responseCode = "401", description = "인증 실패", content = @Content),
            @ApiResponse(responseCode = "403", description = "권한 없음", content = @Content),
            @ApiResponse(responseCode = "404", description = "채팅방 또는 대상 멤버 없음", content = @Content)
    })
    @PostMapping("/{roomId}/members/kick")
    public ResponseEntity<Void> kickMember(
            @PathVariable String roomId,
            @Valid @RequestBody MemberKickRequest request,
            @AuthenticationPrincipal CustomUser owner) {

        chatRoomUseCase.kickMember(roomId, request, owner);
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "채팅방 삭제",
            description = """
            인증 필요. (일반적으로 OWNER 권한 필요)

            - Path: /api/chat/rooms/{roomId}
            - roomId: 채팅방 식별자(ULID 등)
            - 동작: 채팅방 삭제(정책에 따라 논리 삭제일 수 있음)
            """
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "삭제 성공"),
            @ApiResponse(responseCode = "401", description = "인증 실패", content = @Content),
            @ApiResponse(responseCode = "403", description = "권한 없음", content = @Content),
            @ApiResponse(responseCode = "404", description = "채팅방 없음", content = @Content)
    })
    @DeleteMapping("/{roomId}")
    public ResponseEntity<Void> deleteRoom(
            @PathVariable String roomId,
            @AuthenticationPrincipal CustomUser owner) {

        chatRoomUseCase.deleteRoom(roomId, owner);
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "내 채팅방 목록 조회",
            description = """
            인증 필요.

            - Path: /api/chat/rooms
            - 동작: 로그인한 사용자가 참여 중인 채팅방 목록을 반환
            - 응답: List<MyChatRoomResponse>
            """
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "조회 성공",
                    content = @Content(schema = @Schema(implementation = MyChatRoomResponse.class))
            ),
            @ApiResponse(responseCode = "401", description = "인증 실패", content = @Content)
    })
    @GetMapping
    public ResponseEntity<List<MyChatRoomResponse>> myRooms(@AuthenticationPrincipal CustomUser user) {
        return ResponseEntity.ok(chatRoomUseCase.getMyRooms(user));
    }
}
