package org.example.buskmate.messenger.room.repository;

import org.example.buskmate.messenger.room.domain.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

/**
 * {@link ChatRoom} 엔티티에 대한 Spring Data JPA 리포지토리.
 *
 * <p>기본 CRUD 및 페이징/정렬 기능은 {@link JpaRepository}에서 제공한다.</p>
 *
 * <h2>식별자</h2>
 * <ul>
 *   <li>엔티티 타입: {@link ChatRoom}</li>
 *   <li>PK 타입: {@link Long} ({@code ChatRoom.id})</li>
 * </ul>
 *
 * <p>채팅방은 외부 노출용 식별자인 {@code roomId}를 별도로 가지므로,
 * 서비스 계층에서는 주로 {@link #findByRoomId(String)}를 통해 조회한다.</p>
 */
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

    /**
     * 외부 노출용 채팅방 식별자(roomId)로 채팅방을 조회한다.
     *
     * @param roomId 채팅방 식별자(외부 노출용)
     * @return 존재하면 {@link Optional}에 담아 반환
     */
    Optional<ChatRoom> findByRoomId(String roomId);

}
