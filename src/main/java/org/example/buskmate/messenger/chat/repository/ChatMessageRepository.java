package org.example.buskmate.messenger.chat.repository;

import org.example.buskmate.messenger.chat.domain.ChatMessage;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


/**
 * {@link ChatMessage} 엔티티에 대한 Spring Data JPA 리포지토리.
 *
 * <h2>식별자</h2>
 * <ul>
 *   <li>엔티티 타입: {@link ChatMessage}</li>
 *   <li>PK 타입: {@link Long} ({@code ChatMessage.id})</li>
 * </ul>

 */
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

    /**
     * 특정 채팅방(roomId)에서, 외부 노출용 메시지 식별자(messageId)에 대응하는
     * “삭제되지 않은 메시지”의 내부 PK({@code ChatMessage.id})를 조회한다.
     *
     * <p>주로 클라이언트가 들고 있는 {@code messageId}를 커서 페이지네이션에 사용할 수 있도록
     * 내부 PK로 변환하는 목적에 사용한다.</p>
     *
     * @param roomId    채팅방 식별자(외부 노출용)
     * @param messageId 메시지 식별자(외부 노출용, 예: ULID)
     * @return 존재하면 내부 PK를 {@link Optional}로 반환, 없으면 {@link Optional#empty()}
     */
    @Query("""
        select m.id
        from ChatMessage m
        join m.room r
        where r.roomId = :roomId
          and m.messageId = :messageId
          and m.deletedAt is null
    """)
    Optional<Long> findActivePkByRoomIdAndMessageId(@Param("roomId") String roomId,
                                                    @Param("messageId") String messageId);

    /**
     * 특정 채팅방(roomId)의 최근 메시지를 내부 PK 기반 커서(cursorPk)로 조회한다.
     *
     * <p>정렬은 {@code m.id desc}이며, 커서가 주어지면 {@code m.id < cursorPk} 조건으로
     * 더 “이전” 메시지(더 작은 id)를 조회한다.</p>
     *
     * <p>{@code cursorPk}가 {@code null}이면 최초 페이지로 간주하여 최신 메시지부터 조회한다.</p>
     *
     * <p>{@link Pageable}은 페이지 크기 제한을 위해 사용되며,
     * 정렬은 쿼리의 {@code order by m.id desc}가 기준이 된다.</p>
     *
     * @param roomId    채팅방 식별자(외부 노출용)
     * @param cursorPk  내부 PK 커서 (null 가능)
     * @param pageable  조회 크기 제한용 Pageable
     * @return 메시지 목록(최신 → 과거 순)
     */
    @Query("""
        select m
        from ChatMessage m
        join m.room r
        where r.roomId = :roomId
          and m.deletedAt is null
          and (:cursorPk is null or m.id < :cursorPk)
        order by m.id desc
    """)
    List<ChatMessage> findRecentByRoomIdWithCursorPk(@Param("roomId") String roomId,
                                                     @Param("cursorPk") Long cursorPk,
                                                     Pageable pageable);

}
