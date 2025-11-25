package org.example.buskmate.band.domain;

/**
 * <h2>BandStatus</h2>
 *
 * 밴드의 현재 상태를 나타내는 열거형(enum)입니다.
 * <p>
 * 이 값은 밴드의 활성 여부를 표현하며, 삭제는 실제 DB 삭제가 아닌
 * {@code INACTIVE} 상태로 전환되는 소프트 삭제(soft delete) 방식을 사용합니다.
 * </p>
 *
 * <ul>
 *     <li>{@link #ACTIVE} - 정상적으로 활동 중인 밴드</li>
 *     <li>{@link #INACTIVE} - 비활성화된 밴드(삭제 처리에 해당)</li>
 * </ul>
 *
 * <p><b>비고</b><br>
 * 밴드 조회 시 기본적으로 {@code ACTIVE} 상태만 조회되도록 설계하며,
 * {@code INACTIVE} 상태는 사용자에게 노출되지 않습니다.
 * </p>
 */
public enum BandStatus {
    ACTIVE,

    INACTIVE
}
