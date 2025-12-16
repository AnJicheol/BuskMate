package org.example.buskmate.messenger.room.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * 채팅방 멤버 초대 요청 DTO.
 *
 * <p>특정 채팅방에 초대(추가)할 대상 사용자의 식별자를 전달한다.</p>
 *
 * <h2>검증</h2>
 * <ul>
 *   <li>{@link NotBlank}: {@code memberId}는 null/빈 문자열/공백만으로 구성될 수 없다.</li>
 * </ul>
 *
 * <h2>예시(JSON)</h2>
 * <pre>{@code
 * { "memberId": "01JABCDE...ULID" }
 * }</pre>
 *
 * @param memberId 초대할 사용자 식별자
 */
public record MemberKickRequest(
        @NotBlank String memberId
) {}
