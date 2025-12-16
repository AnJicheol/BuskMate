package org.example.buskmate.messenger.chat.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.buskmate.messenger.room.domain.ChatRoom;

import java.time.LocalDateTime;


/**
 * 채팅 메시지 엔티티.
 *
 * <p>특정 {@link ChatRoom}에 속한 메시지를 저장한다.
 * 메시지는 외부 노출용 식별자인 {@code messageId}를 별도로 가지며, 논리 삭제를 위해 {@code deletedAt}을 사용한다.</p>
 *
 * <h2>식별자</h2>
 * <ul>
 *   <li>{@code id}: DB 내부 PK (Auto Increment)</li>
 *   <li>{@code messageId}: 외부 노출/클라이언트 식별용 문자열(예: ULID 26자)</li>
 * </ul>
 *
 * <h2>삭제 정책</h2>
 * <p>물리 삭제 대신 {@code deletedAt}을 설정하여 논리 삭제를 수행한다.
 * {@link #isActive()}로 활성 여부를 판단한다.</p>
 */
@Entity
@Getter
@Table(
        name = "chat_message",
        indexes = {
                @Index(name = "idx_chat_message_room_id_id", columnList = "room_id, id"),
        }
)
@NoArgsConstructor
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "message_id", nullable = false, length = 26, unique = true)
    private String messageId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "room_id",
            nullable = false
    )
    private ChatRoom room;

    // 보낸 사람 (Users PK)
    @Column(name = "sender_id", nullable = false, length = 26)
    private String senderId;

    // 텍스트 내용
    @Column(name = "content", columnDefinition = "text")
    private String content;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;


    public ChatMessage(String messageId, ChatRoom room, String senderId, String content) {
        this.messageId = messageId;
        this.room = room;
        this.senderId = senderId;
        this.content = content;
    }

    @PrePersist
    void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.deletedAt = null;
    }

    public boolean isActive() {
        return deletedAt == null;
    }

}