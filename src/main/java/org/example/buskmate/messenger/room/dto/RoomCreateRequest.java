package org.example.buskmate.messenger.room.dto;

import lombok.Getter;

/**
 * 채팅방 생성 요청 DTO.
 *
 * <p>새 그룹 채팅방을 생성할 때 사용한다.</p>
 *
 * <h2>필드</h2>
 * <ul>
 *   <li>{@code roomTitle}: 생성할 채팅방 제목</li>
 * </ul>
 *
 * <h2>예시(JSON)</h2>
 * <pre>{@code
 * { "roomTitle": "프로젝트 회의방" }
 * }</pre>
 *
 */
@Getter
public class RoomCreateRequest {
    private String roomTitle;
}
