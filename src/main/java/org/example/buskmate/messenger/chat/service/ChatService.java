package org.example.buskmate.messenger.chat.service;

import org.example.buskmate.messenger.chat.domain.ChatMessage;
import org.example.buskmate.messenger.chat.dto.ChatMessageResponse;
import org.example.buskmate.messenger.room.domain.ChatRoom;

import java.util.List;

/**
 * 채팅 메시지 도메인 서비스 인터페이스.
 *
 * <p>채팅 메시지의 생성/저장을 담당한다. 일반적으로 다음 책임을 가진다.</p>
 * <ul>
 *   <li>메시지 식별자 생성(예: ULID)</li>
 *   <li>{@link ChatMessage} 생성 및 영속화</li>
 *   <li>(필요 시) 논리 삭제/복구, 조회 보조 기능 확장</li>
 * </ul>
 *
 * <p><b>주의:</b> 방 멤버십 검증, 권한 체크 등은 보통 상위 유스케이스 계층에서 처리하고,
 * 이 서비스는 “저장”에 집중하도록 설계하는 편이 깔끔하다.</p>
 */
public interface ChatService {

    /**
     * 채팅방에 새 메시지를 저장한다.
     *
     * @param room     메시지가 속할 채팅방
     * @param senderId 발신자 식별자
     * @param content  메시지 본문
     * @return 저장된 {@link ChatMessage} (식별자/생성 시각 등이 채워진 상태)
     */
    ChatMessage save(ChatRoom room, String senderId, String content);

    /**
     * 채팅방의 메시지 목록을 커서 기반으로 조회한다.
     *
     * <p>조회는 “삭제되지 않은 메시지”만 대상으로 하며, 내부 PK({@code id})를 기준으로
     * 키셋(커서) 페이지네이션을 수행한다.</p>
     *
     * <h2>커서 규칙</h2>
     * <ul>
     *   <li>{@code cursorPk == null}: 최초 페이지(최신 메시지부터)</li>
     *   <li>{@code cursorPk != null}: {@code id < cursorPk} 조건으로 더 과거 메시지를 조회</li>
     * </ul>
     *
     * <h2>정렬</h2>
     * <p>최신 → 과거 순(내림차순)으로 반환한다.</p>
     *
     * @param roomId   채팅방 식별자(외부 노출용)
     * @param cursorPk 커서로 사용할 메시지 내부 PK (null 가능)
     * @param size     조회할 메시지 개수(페이지 크기)
     * @return 메시지 응답 DTO 목록(최신 → 과거 순)
     * @throws IllegalArgumentException roomId가 null/blank 이거나 size가 비정상 값인 경우(구현 정책에 따름)
     */
    List<ChatMessageResponse> getMessages(String roomId, Long cursorPk, int size);


    /**
     * 외부 노출용 메시지 식별자({@code messageId})를 내부 PK 커서({@code id})로 변환한다.
     *
     * <p>클라이언트가 커서로 {@code messageId}(예: ULID)를 들고 있을 때,
     * DB 조회를 위한 내부 PK({@code Long})로 변환하는 용도로 사용한다.</p>
     *
     * <p>변환 대상은 “해당 채팅방에 속하면서 삭제되지 않은 메시지”로 한정한다.</p>
     *
     * @param roomId    채팅방 식별자(외부 노출용)
     * @param messageId 메시지 식별자(외부 노출용)
     * @return 내부 PK 커서 값
     * @throws IllegalArgumentException messageId가 유효하지 않거나(없거나/삭제됨/방 불일치) 변환 불가한 경우(구현 정책에 따름)
     */
    Long getCursorPk(String roomId, String messageId);
}
