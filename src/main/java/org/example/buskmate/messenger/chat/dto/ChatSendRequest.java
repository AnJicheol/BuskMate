package org.example.buskmate.messenger.chat.dto;

/**
 * 채팅 메시지 전송 요청 DTO.
 *
 * <p>클라이언트가 특정 채팅방으로 메시지를 전송할 때 사용하는 입력 모델이다.
 * 본 DTO는 메시지 본문만 포함하며, 발신자/채팅방 정보는 전송 채널(STOMP destination 또는 HTTP path)과
 * 인증된 사용자 정보(Principal)로부터 결정된다.</p>
 *
 * <h2>예시(JSON)</h2>
 * <pre>{@code
 * { "content": "hello" }
 * }</pre>
 *
 * @param content 메시지 본문
 */
public record ChatSendRequest(String content) { }