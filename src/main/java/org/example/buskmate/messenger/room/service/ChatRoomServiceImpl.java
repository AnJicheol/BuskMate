package org.example.buskmate.messenger.room.service;


import com.github.f4b6a3.ulid.UlidCreator;
import lombok.RequiredArgsConstructor;
import org.example.buskmate.messenger.room.domain.ChatRoom;
import org.example.buskmate.messenger.room.domain.ChatRoomStatus;
import org.example.buskmate.messenger.room.repository.ChatRoomRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * {@link ChatRoomService} 구현체.
 *
 * <p>채팅방 생성/조회/삭제(논리 삭제)를 담당한다.</p>
 *
 * <h2>주요 동작</h2>
 * <ul>
 *   <li>채팅방 생성 시 외부 노출용 {@code roomId}를 ULID로 발급한다.</li>
 *   <li>조회/삭제 시 삭제된 방({@link ChatRoomStatus#DELETED})은 예외 처리한다.</li>
 *   <li>삭제는 물리 삭제가 아닌 상태 전환(soft delete)으로 처리한다.</li>
 * </ul>
 *
 * <h2>트랜잭션</h2>
 * <ul>
 *   <li>생성/삭제: {@code @Transactional}</li>
 *   <li>조회: {@code @Transactional(readOnly = true)}</li>
 * </ul>
 *
 * <h2>예외</h2>
 * <ul>
 *   <li>{@link IllegalArgumentException}: 채팅방이 존재하지 않는 경우</li>
 *   <li>{@link IllegalStateException}: 삭제된 채팅방을 조회/삭제하려는 경우</li>
 * </ul>
 */
@Service
@RequiredArgsConstructor
public class ChatRoomServiceImpl implements ChatRoomService{
    private final ChatRoomRepository chatRoomRepository;

    /**
     * {@inheritDoc}
     *
     * <p>ULID 기반 {@code roomId}를 생성한 뒤 {@link ChatRoom}을 저장한다.</p>
     *
     * @param roomTitle 채팅방 제목
     * @return 생성된 채팅방 엔티티
     */
    @Override
    @Transactional
    public ChatRoom createChatRoom(String roomTitle) {
        String roomId = UlidCreator.getUlid().toString();

        ChatRoom chatRoom = new ChatRoom(roomId, roomTitle);
        chatRoomRepository.save(chatRoom);
        return chatRoom;
    }

    /**
     * {@inheritDoc}
     *
     * <p>존재하지 않으면 {@link IllegalArgumentException}을 던지고,
     * 삭제된 방이면 {@link IllegalStateException}을 던진다.</p>
     *
     * @param roomId 채팅방 식별자(외부 노출용)
     * @return 조회된 채팅방 (삭제되지 않은 상태)
     */
    @Override
    @Transactional(readOnly = true)
    public ChatRoom getByRoomId(String roomId) {
        ChatRoom room = chatRoomRepository.findByRoomId(roomId)
                .orElseThrow(() -> new IllegalArgumentException("채팅방이 존재하지 않습니다."));

        if (room.getStatus() == ChatRoomStatus.DELETED) {
            throw new IllegalStateException("삭제된 채팅방입니다.");
        }
        return room;
    }

    /**
     * {@inheritDoc}
     *
     * <p>채팅방을 논리 삭제한다. 이미 삭제된 방이면 예외를 던진다.</p>
     *
     * @param roomId 채팅방 식별자(외부 노출용)
     */

    @Override
    @Transactional
    public void softDelete(String roomId) {
        ChatRoom room = chatRoomRepository.findByRoomId(roomId)
                .orElseThrow(() -> new IllegalArgumentException("채팅방이 존재하지 않습니다."));

        if (room.getStatus() == ChatRoomStatus.DELETED) {
            throw new IllegalStateException("삭제된 채팅방입니다.");
        }
        room.delete();
    }

}
