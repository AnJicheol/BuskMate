package org.example.buskmate.messenger.room.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 인증된 사용자 정보를 표현하는 커스텀 Principal(또는 Security Context의 사용자 모델).
 *
 * <p>컨트롤러에서 {@code @AuthenticationPrincipal CustomUser} 형태로 주입받아
 * 현재 로그인한 사용자의 식별자를 접근하는 용도로 사용한다.</p>
 *
 * <h2>필드</h2>
 * <ul>
 *   <li>{@code userId}: 사용자 식별자(프로젝트 정책에 따른 외부 노출용 ID, 예: ULID)</li>
 * </ul>
 *
 */
@Getter
@AllArgsConstructor
public class CustomUser {
    private String userId;
}
