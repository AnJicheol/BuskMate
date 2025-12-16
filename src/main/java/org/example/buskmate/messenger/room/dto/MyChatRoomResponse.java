package org.example.buskmate.messenger.room.dto;

import org.example.buskmate.messenger.room.domain.ChatRoomRole;
import java.time.LocalDateTime;

/**
 * 내 채팅방 목록 조회 응답 DTO.
 *
 * <p>로그인한 사용자가 참여 중인 채팅방을 리스트 형태로 보여주기 위한 요약 정보 모델이다.</p>
 *
 * <h2>필드 의미</h2>
 * <ul>
 *   <li>{@code roomId}: 채팅방 식별자(외부 노출용)</li>
 *   <li>{@code title}: 채팅방 제목</li>
 *   <li>{@code myRole}: 해당 채팅방에서의 내 역할(예: OWNER, MEMBER)</li>
 *   <li>{@code lastMessageAt}: 마지막 메시지 시각(없으면 null일 수 있음)</li>
 * </ul>
 *
 * <p><b>용도:</b> 채팅방 리스트 화면/사이드바 등에서 사용한다.</p>
 *
 * @param roomId 채팅방 식별자
 * @param title 채팅방 제목
 * @param myRole 내 역할
 * @param lastMessageAt 마지막 메시지 시각 (메시지가 없으면 null 가능)
 */
public record MyChatRoomResponse(
        String roomId,
        String title,
        ChatRoomRole myRole,
        LocalDateTime lastMessageAt
) {}
