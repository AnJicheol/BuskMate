package org.example.buskmate.messenger.domain;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "chat_room")
@NoArgsConstructor
public class ChatRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "room_id", nullable = false, length = 100, unique = true)
    private String roomKey;

    @Column(name = "title", nullable = false, length = 100)
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private ChatRoomStatus status; // ACTIVE, DELETED

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;


    public ChatRoom(String roomKey, String title) {
        this.roomKey = roomKey;
        this.title = title;
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.status = ChatRoomStatus.ACTIVE;
    }

}
