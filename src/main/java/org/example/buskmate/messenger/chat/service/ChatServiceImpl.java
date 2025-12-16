package org.example.buskmate.messenger.chat.service;

import com.github.f4b6a3.ulid.UlidCreator;
import lombok.RequiredArgsConstructor;
import org.example.buskmate.messenger.chat.domain.ChatMessage;
import org.example.buskmate.messenger.chat.dto.ChatMessageResponse;
import org.example.buskmate.messenger.chat.repository.ChatMessageRepository;
import org.example.buskmate.messenger.room.domain.ChatRoom;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * {@link ChatService} 구현체.
 *
 * <p>새 채팅 메시지를 생성하고 영속화한다.</p>
 *
 * <h2>주요 동작</h2>
 * <ul>
 *   <li>외부 노출용 메시지 식별자({@code messageId})를 ULID로 생성한다.</li>
 *   <li>{@link ChatMessage} 엔티티를 생성한 뒤 {@link ChatMessageRepository}를 통해 저장한다.</li>
 * </ul>
 *
 * <p><b>트랜잭션:</b> 메시지 생성과 저장을 하나의 트랜잭션으로 처리한다.</p>
 */
@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService{
    private final ChatMessageRepository chatMessageRepository;


    /**
     * {@inheritDoc}
     *
     * <p>메시지 식별자({@code messageId})는 ULID로 생성한다.</p>
     *
     * @param room     메시지가 속할 채팅방
     * @param senderId 발신자 식별자
     * @param content  메시지 본문
     * @return 저장된 {@link ChatMessage}
     */
    @Override
    @Transactional
    public ChatMessage save(ChatRoom room, String senderId, String content) {
        String messageId = UlidCreator.getUlid().toString();
        return chatMessageRepository.save(new ChatMessage(messageId, room, senderId, content));
    }


    /**
     * {@inheritDoc}
     *
     * <p>특정 채팅방의 메시지 목록을 커서 기반(키셋) 페이지네이션으로 조회한다.</p>
     *
     * <h2>조회/정렬 규칙</h2>
     * <ul>
     *   <li>삭제되지 않은 메시지({@code deletedAt is null})만 조회한다.</li>
     *   <li>{@code cursorPk == null}이면 최신 메시지부터 조회한다.</li>
     *   <li>{@code cursorPk != null}이면 {@code id < cursorPk} 조건으로 더 과거 메시지를 조회한다.</li>
     *   <li>정렬은 내부 PK 기준 내림차순(최신 → 과거)이다.</li>
     * </ul>
     *
     * <h2>페이징</h2>
     * <p>{@link PageRequest#of(int, int)}를 사용해 조회 개수({@code size})를 제한한다.
     * 커서 페이지네이션 특성상 페이지 번호는 항상 0으로 고정한다.</p>
     *
     * <h2>DTO 변환</h2>
     * <p>{@link ChatMessage} 엔티티를 {@link ChatMessageResponse}로 매핑한다.
     * {@code roomId}는 조인으로부터 재조회하지 않고, 입력 파라미터 값을 그대로 사용한다.</p>
     *
     * @param roomId   채팅방 식별자(외부 노출용)
     * @param cursorPk 내부 PK 커서(null 가능)
     * @param size     조회 개수(페이지 크기)
     * @return 메시지 응답 DTO 목록(최신 → 과거 순)
     */
    @Override
    @Transactional(readOnly = true)
    public List<ChatMessageResponse> getMessages(String roomId, Long cursorPk, int size) {

        return chatMessageRepository.findRecentByRoomIdWithCursorPk(roomId, cursorPk, PageRequest.of(0, size))
                .stream()
                .map(m -> new ChatMessageResponse(
                        roomId,
                        m.getMessageId(),
                        m.getSenderId(),
                        m.getContent(),
                        m.getCreatedAt()
                ))
                .toList();
    }

    /**
     * {@inheritDoc}
     *
     * <p>클라이언트가 보낸 {@code messageId}(외부 노출용)를 기반으로,
     * 커서 페이지네이션에 사용할 내부 PK({@code ChatMessage.id})를 조회한다.</p>
     *
     * <h2>동작 규칙</h2>
     * <ul>
     *   <li>{@code messageId}가 {@code null}이거나 blank이면 커서를 사용하지 않는 최초 조회로 간주하고 {@code null}을 반환한다.</li>
     *   <li>{@code messageId}가 유효하면, 해당 {@code roomId}에 속하고 삭제되지 않은 메시지의 내부 PK를 조회한다.</li>
     *   <li>조회 대상이 없으면 {@link IllegalArgumentException}을 던진다.</li>
     * </ul>
     *
     * @param roomId    채팅방 식별자(외부 노출용)
     * @param messageId 메시지 식별자(외부 노출용, 예: ULID). null/blank 가능
     * @return 커서로 사용할 내부 PK. {@code messageId}가 null/blank이면 {@code null}
     * @throws IllegalArgumentException {@code messageId}가 주어졌으나 해당 메시지가 존재하지 않거나(삭제/방 불일치 포함) 커서 변환이 불가한 경우
     */
    @Override
    @Transactional(readOnly = true)
    public Long getCursorPk(String roomId, String messageId) {
        Long cursorPk = null;
        if (messageId != null && !messageId.isBlank()) {
            cursorPk = chatMessageRepository.findActivePkByRoomIdAndMessageId(roomId, messageId)
                    .orElseThrow(() -> new IllegalArgumentException("커서 메시지가 존재하지 않습니다."));
        }
        return cursorPk;
    }

}
