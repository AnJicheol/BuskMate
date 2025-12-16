package org.example.buskmate.messenger.room.repository;

import org.example.buskmate.messenger.room.domain.ChatRoom;
import org.example.buskmate.messenger.room.domain.ChatRoomMember;
import org.example.buskmate.messenger.room.dto.MyChatRoomResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * {@link ChatRoomMember} 엔티티에 대한 Spring Data JPA 리포지토리.
 *
 * <p>채팅방 멤버십 조회/검증, 방 나가기 처리, “내 채팅방 목록” 조회와 같은
 * 멤버십 중심의 쿼리를 제공한다.</p>
 *
 * <h2>주요 조회 패턴</h2>
 * <ul>
 *   <li>특정 방에서 특정 유저의 멤버십 조회/존재 확인</li>
 *   <li>특정 방의 활성 멤버 일괄 나가기 처리</li>
 *   <li>특정 유저의 참여 방 목록을 “마지막 메시지 시각” 기준으로 정렬하여 조회</li>
 * </ul>
 *
 * <p><b>주의:</b> “활성 멤버”의 기준은 {@code leftAt is null}로 정의한다.</p>
 */
public interface ChatMemberRepository extends JpaRepository<ChatRoomMember, Long> {

    /**
     * 특정 채팅방에서 특정 사용자의 멤버십을 조회한다.
     *
     * <p>유니크 제약(방+유저)이 보장된다면 최대 1건이 조회된다.</p>
     *
     * @param room   채팅방 엔티티
     * @param userId 사용자 식별자
     * @return 멤버십이 존재하면 {@link Optional}에 담아 반환
     */
    Optional<ChatRoomMember> findByRoomAndUserId(ChatRoom room, String userId);

    /**
     * 특정 채팅방에서 특정 사용자의 멤버십 존재 여부를 반환한다.
     *
     * @param room   채팅방 엔티티
     * @param userId 사용자 식별자
     * @return 존재하면 {@code true}
     */
    boolean existsByRoomAndUserId(ChatRoom room, String userId);

    /**
     * 특정 채팅방의 “활성 멤버”(leftAt이 null)들을 일괄적으로 나가기 처리한다.
     *
     * <p>예: 채팅방 삭제/종료 시점에 남아있는 멤버들을 한 번에 leave 처리할 때 사용.</p>
     *
     * <p><b>동작:</b> {@code leftAt = :now}로 업데이트한다.</p>
     *
     * @param room 채팅방
     * @param now  나가기 처리 시각
     * @return 업데이트된 행(row) 수
     */
    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("""
        update ChatRoomMember m
        set m.leftAt = :now
        where m.room = :room
          and m.leftAt is null
    """)
    int leaveAllActiveByRoom(@Param("room") ChatRoom room,
                             @Param("now") LocalDateTime now);

    /**
     * 특정 사용자가 참여 중인 채팅방 목록을 마지막 메시지 시각 기준으로 정렬하여 조회한다.
     *
     * <p>정렬 규칙:</p>
     * <ol>
     *   <li>메시지가 있는 방이 메시지 없는 방보다 먼저 오도록 정렬한다.</li>
     *   <li>마지막 메시지 시각이 최신인 방이 먼저 오도록 내림차순 정렬한다.</li>
     *   <li>동률일 경우 방의 DB PK({@code r.id}) 내림차순으로 정렬한다.</li>
     * </ol>
     *
     * <p>조회 대상:</p>
     * <ul>
     *   <li>{@code m.userId = :userId}</li>
     *   <li>활성 멤버십: {@code m.leftAt is null}</li>
     *   <li>활성 채팅방: {@code r.status = ACTIVE}</li>
     *   <li>삭제되지 않은 메시지만 집계: {@code msg.deletedAt is null}</li>
     * </ul>
     *
     * <p><b>반환:</b> {@link MyChatRoomResponse} DTO로 프로젝션한다.</p>
     *
     * @param userId 사용자 식별자
     * @return 내 채팅방 목록(정렬된 상태)
     */
    @Query("""
        select new org.example.buskmate.messenger.room.dto.MyChatRoomResponse(
            r.roomId,
            r.title,
            m.role,
            max(msg.createdAt)
        )
        from ChatRoomMember m
        join m.room r
        left join ChatMessage msg
               on msg.room = r
              and msg.deletedAt is null
        where m.userId = :userId
          and m.leftAt is null
          and r.status = org.example.buskmate.messenger.room.domain.ChatRoomStatus.ACTIVE
        group by r.id, r.roomId, r.title, m.role
        order by
          case when max(msg.createdAt) is null then 1 else 0 end,
          max(msg.createdAt) desc,
          r.id desc
    """)
    List<MyChatRoomResponse> findMyRoomsOrderByLastMessage(@Param("userId") String userId);

}
