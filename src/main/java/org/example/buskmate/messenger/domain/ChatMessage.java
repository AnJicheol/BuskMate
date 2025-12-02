package org.example.buskmate.messenger.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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