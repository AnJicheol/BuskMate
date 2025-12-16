package org.example.buskmate.messenger.chat.dto;

import java.time.LocalDateTime;

/**
 * 채팅 메시지 응답 DTO.
 *
 * <p>클라이언트에 “메시지 1건”을 전달하기 위한 불변(immutable) 응답 모델이다.
 * 주로 다음 상황에서 사용된다.</p>
 *
 * <ul>
 *   <li>STOMP 브로드캐스트 페이로드</li>
 *   <li>채팅 메시지 조회(HTTP) 응답</li>
 * </ul>
 *
 * <h2>필드 의미</h2>
 * <ul>
 *   <li>{@code roomId}: 메시지가 속한 채팅방 식별자(외부 노출용)</li>
 *   <li>{@code messageId}: 메시지 식별자(외부 노출용, 예: ULID 26자)</li>
 *   <li>{@code senderId}: 발신자 식별자</li>
 *   <li>{@code content}: 메시지 본문</li>
 *   <li>{@code createdAt}: 메시지 생성 시각</li>
 * </ul>
 *
 * <p><b>주의:</b> {@link LocalDateTime} 직렬화 형식은 전송 채널(STOMP/HTTP)과 Jackson 설정에 따라 달라질 수 있다.</p>
 *
 * @param roomId 메시지가 속한 채팅방 식별자
 * @param messageId 메시지 식별자
 * @param senderId 발신자 식별자
 * @param content 메시지 본문
 * @param createdAt 생성 시각
 */
public record ChatMessageResponse(
        String roomId,
        String messageId,
        String senderId,
        String content,
        LocalDateTime createdAt
) {}
