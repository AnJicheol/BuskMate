package org.example.buskmate.messenger.room.domain;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 채팅방 엔티티.
 *
 * <p>채팅이 이루어지는 “방”을 표현한다. 외부 노출용 식별자인 {@code roomId}를 별도로 가지며,
 * 삭제는 물리 삭제 대신 {@link ChatRoomStatus}를 이용한 상태 기반(논리 삭제)으로 처리한다.</p>
 *
 * <h2>식별자</h2>
 * <ul>
 *   <li>{@code id}: DB 내부 PK (Auto Increment)</li>
 *   <li>{@code roomId}: 외부 노출/클라이언트 참조용 방 식별자(예: ULID 등)</li>
 * </ul>
 *
 * <h2>상태</h2>
 * <ul>
 *   <li>{@link ChatRoomStatus#ACTIVE}: 사용 가능한 채팅방</li>
 *   <li>{@link ChatRoomStatus#DELETED}: 삭제된 채팅방(논리 삭제)</li>
 * </ul>
 *
 * <p>{@link #delete()}는 멱등(idempotent)하게 동작한다. (이미 삭제된 경우 변화 없음)</p>
 */
@Entity
@Getter
@Table(name = "chat_room")
@NoArgsConstructor
public class ChatRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "room_id", nullable = false, length = 100, unique = true)
    private String roomId;

    @Column(name = "title", nullable = false, length = 100)
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private ChatRoomStatus status; // ACTIVE, DELETED

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;


    public ChatRoom(String roomId, String title) {
        this.roomId = roomId;
        this.title = title;
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.status = ChatRoomStatus.ACTIVE;
    }

    public boolean isDeleted() {
        return this.status == ChatRoomStatus.DELETED;
    }

    public void delete() {
        if (this.status == ChatRoomStatus.DELETED) {
            return;
        }
        this.status = ChatRoomStatus.DELETED;
    }
}
