package org.example.buskmate.messenger.domain;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
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

    @Column(name = "uid", nullable = false, length = 26, unique = true)
    private String uid; // ULID

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

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public ChatMessage(ChatRoom room, String senderId, String content, LocalDateTime createdAt) {
        this.room = room;
        this.senderId = senderId;
        this.content = content;
        this.createdAt = createdAt;
    }
}