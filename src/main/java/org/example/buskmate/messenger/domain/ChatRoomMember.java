package org.example.buskmate.messenger.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "chat_room_member",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_chat_room_member_room_user",
                        columnNames = {"room_id", "user_id"}
                )
        }
)
public class ChatRoomMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", nullable = false)
    private ChatRoom room;

    @Column(name = "user_id", nullable = false)
    private Long userId; // Users PK

    // 채팅 도메인 관점 상태만 둔다
    @Column(name = "muted", nullable = false)
    private boolean muted = false;

    @Column(name = "hidden", nullable = false)
    private boolean hidden = false; // 유저가 방 목록에서 숨김 여부

    @Column(name = "joined_at", nullable = false, updatable = false)
    private LocalDateTime joinedAt;

    @Column(name = "left_at")
    private LocalDateTime leftAt;

    // 읽음 상태
    @Column(name = "last_read_message_id")
    private Long lastReadMessageId;

    @Column(name = "last_read_at")
    private LocalDateTime lastReadAt;

    @PrePersist
    void onCreate() {
        this.joinedAt = LocalDateTime.now();
    }

    public boolean isActive() {
        return leftAt == null;
    }
}