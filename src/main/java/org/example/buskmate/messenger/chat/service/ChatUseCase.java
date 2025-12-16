package org.example.buskmate.messenger.chat.service;


import lombok.RequiredArgsConstructor;
import org.example.buskmate.messenger.chat.domain.ChatMessage;
import org.example.buskmate.messenger.chat.dto.ChatMessageResponse;
import org.example.buskmate.messenger.room.domain.ChatRoom;
import org.example.buskmate.messenger.room.service.ChatMemberService;
import org.example.buskmate.messenger.room.service.ChatRoomService;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * 채팅 유스케이스(애플리케이션 서비스).
 *
 * <p>채팅 메시지 전송 시나리오를 오케스트레이션한다.</p>
 *
 * <h2>책임</h2>
 * <ul>
 *   <li>입력 검증(채팅방 ID, 메시지 내용)</li>
 *   <li>채팅방 조회</li>
 *   <li>멤버십(권한) 검증</li>
 *   <li>메시지 저장</li>
 *   <li>클라이언트 전달용 DTO({@link ChatMessageResponse}) 변환</li>
 * </ul>
 *
 * <p><b>트랜잭션:</b> 방 조회 → 멤버 검증 → 메시지 저장을 하나의 트랜잭션으로 처리한다.</p>
 *
 * <p><b>예외:</b> 입력 값이 비어 있거나 유효하지 않으면 {@link IllegalArgumentException}을 던진다.
 * (방 미존재, 멤버 아님 등의 도메인 예외는 하위 서비스에서 발생할 수 있다.)</p>
 */
@Service
@RequiredArgsConstructor
public class ChatUseCase {
    private final ChatRoomService chatRoomService;
    private final ChatMemberService chatMemberService;
    private final ChatService chatMessageService;

    /**
     * 채팅방에 메시지를 전송(저장)하고, 응답 DTO를 반환한다.
     *
     * <p>호출자는 일반적으로 STOMP 컨트롤러 또는 HTTP 컨트롤러이며,
     * {@code senderId}는 인증된 사용자(Principal)에서 확보되는 것을 전제한다.</p>
     *
     * @param roomId   채팅방 식별자(외부 노출용)
     * @param senderId 발신자 식별자
     * @param content  메시지 본문
     * @return 저장된 메시지 정보를 담은 {@link ChatMessageResponse}
     * @throws IllegalArgumentException roomId 또는 content가 null/blank 인 경우
     */
    @Transactional
    public ChatMessageResponse sendMessage(String roomId, String senderId, String content){
        if (roomId == null || roomId.isBlank()) throw new IllegalArgumentException("채팅방 ID가 올바르지 않습니다.");
        if (content == null || content.isBlank()) throw new IllegalArgumentException("메시지 내용은 필수입니다.");

        ChatRoom room = chatRoomService.getByRoomId(roomId);
        chatMemberService.validMember(room, senderId);
        ChatMessage saved = chatMessageService.save(room, senderId, content);

        return new ChatMessageResponse(
                room.getRoomId(),
                saved.getMessageId(),
                saved.getSenderId(),
                saved.getContent(),
                saved.getCreatedAt()
        );
    }

    /**
     * 채팅방 메시지 목록을 조회한다.
     *
     * <p>처리 흐름:</p>
     * <ol>
     *   <li>roomId/size 입력 검증( size는 1~200, 범위 밖이면 30으로 보정 )</li>
     *   <li>채팅방 조회 및 요청자 멤버십 검증</li>
     *   <li>cursorMessageId(외부 메시지 ID)를 내부 PK 커서로 변환</li>
     *   <li>커서 기반으로 최근 메시지 목록 반환</li>
     * </ol>
     *
     * @param roomId          채팅방 식별자
     * @param requesterId     요청자 사용자 식별자
     * @param cursorMessageId 커서 메시지 ID(null/blank 가능)
     * @param size            조회 개수(기본 보정 30)
     * @return 메시지 목록(최신 → 과거)
     */
    @Transactional
    public List<ChatMessageResponse> getMessages(String roomId, String requesterId, String cursorMessageId, int size) {
        if (roomId == null || roomId.isBlank()) {
            throw new IllegalArgumentException("채팅방 ID가 올바르지 않습니다.");
        }
        if (size <= 0 || size > 200) size = 30;

        ChatRoom room = chatRoomService.getByRoomId(roomId);
        chatMemberService.validMember(room, requesterId);

        Long cursorPk = chatMessageService.getCursorPk(roomId, cursorMessageId);

        return chatMessageService.getMessages(roomId, cursorPk, size);
    }

}
