package org.example.buskmate.messenger.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(
        name = "chat_room_member",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_chat_room_member",
                        columnNames = {"room_id", "user_id"}
                )
        }
)
@NoArgsConstructor
public class ChatRoomMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", nullable = false)
    private ChatRoom room;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false, length = 20)
    private ChatRoomRole role;

    @Column(name = "joined_at", updatable = false)
    private LocalDateTime joinedAt;

    @Column(name = "left_at")
    private LocalDateTime leftAt;

    // 읽음 상태
    @Column(name = "last_read_message_id")
    private Long lastReadMessageId;

    @Column(name = "last_read_at")
    private LocalDateTime lastReadAt;


    public ChatRoomMember(ChatRoom room, String userId, ChatRoomRole role) {
        this.room = room;
        this.userId = userId;
        this.role = role;
        // joinedAt, leftAt, lastRead* 은 나중에 도메인 메서드로 채움
    }

    @PrePersist
    void onCreate() {
        this.joinedAt = LocalDateTime.now();
    }

    public boolean isActive() {
        return leftAt == null;
    }

    // 방 나가기
    public void leave() {
        this.leftAt = LocalDateTime.now();
    }

    // 메시지 읽음 처리
    public void markRead(Long messageId) {
        this.lastReadMessageId = messageId;
        this.lastReadAt = LocalDateTime.now();
    }

}