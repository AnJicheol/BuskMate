package org.example.buskmate.messenger.room.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 채팅방 멤버 엔티티.
 *
 * <p>사용자({@code userId})가 특정 {@link ChatRoom}에 참여한 상태를 표현한다.</p>
 *
 * <h2>유니크 제약</h2>
 * <p>{@code (room_id, user_id)}는 유니크해야 한다. 즉, 동일한 사용자는 동일한 채팅방에
 * “동시에” 중복 가입될 수 없다. (재가입 정책이 필요하면 {@code leftAt} 처리 방식/유니크 정책을 함께 고려)</p>
 *
 * <h2>상태/이력</h2>
 * <ul>
 *   <li>{@code joinedAt}: 최초 가입 시각 (영속화 시점에 설정)</li>
 *   <li>{@code leftAt}: 방 나가기 시각 (null이면 활성 멤버)</li>
 *   <li>읽음 상태: 마지막으로 읽은 메시지 및 시각</li>
 * </ul>
 *
 * <p><b>활성 여부:</b> {@link #isActive()}가 {@code true}면 현재 방에 남아 있는 멤버이다.</p>
 */
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