package org.example.buskmate.messenger.domain;


import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "chat_room",
        indexes = {
                @Index(name = "uk_chat_room_key", columnList = "room_key", unique = true)
        }
)
public class ChatRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "room_key", nullable = false, length = 100, unique = true)
    private String roomKey;

    @Column(name = "title", nullable = false, length = 100)
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private ChatRoomStatus status; // ACTIVE, DELETED

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;


    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.status = ChatRoomStatus.ACTIVE;
    }

}
