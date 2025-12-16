package org.example.buskmate.messenger.room.service;

import org.example.buskmate.messenger.room.domain.ChatRoom;

/**
 * 채팅방 도메인 서비스 인터페이스.
 *
 * <p>채팅방 생성/조회/삭제(논리 삭제)와 같은 “채팅방 자체”의 라이프사이클을 관리한다.</p>
 *
 * <h2>주요 책임</h2>
 * <ul>
 *   <li>채팅방 생성</li>
 *   <li>외부 노출용 식별자(roomId) 기준 조회</li>
 *   <li>채팅방 삭제(정책에 따라 soft delete)</li>
 * </ul>
 *
 * <p><b>주의:</b> 멤버십(초대/강퇴/검증)은 {@code ChatMemberService}가 담당하는 것이 일반적이며,
 * 이 서비스는 “방” 엔티티의 상태 관리에 집중한다.</p>
 */
public interface ChatRoomService {

    /**
     * 새 채팅방을 생성한다.
     *
     * <p>구현체는 보통 외부 노출용 {@code roomId}(예: ULID)를 발급하고,
     * {@link ChatRoom} 엔티티를 저장한 뒤 반환한다.</p>
     *
     * @param roomTitle 채팅방 제목
     * @return 생성된 {@link ChatRoom}
     */
    ChatRoom createChatRoom(String roomTitle);

    /**
     * 외부 노출용 채팅방 식별자(roomId)로 채팅방을 조회한다.
     *
     * <p>존재하지 않으면 예외를 던지는 형태로 구현되는 것이 일반적이다.</p>
     *
     * @param roomId 채팅방 식별자(외부 노출용)
     * @return 조회된 {@link ChatRoom}
     */
    ChatRoom getByRoomId(String roomId);

    /**
     * 채팅방을 논리 삭제(soft delete)한다.
     *
     * <p>정책에 따라 상태 전환하거나,
     * 삭제 시점에 멤버 정리 등의 부가 작업이 수행될 수 있다.</p>
     *
     * @param roomId 채팅방 식별자(외부 노출용)
     */
    void softDelete(String roomId);
}
