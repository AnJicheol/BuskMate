package org.example.buskmate.band.domain;

/**
 * 밴드의 현재 상태를 나타내는 열거형입니다.
 *
 * <p>
 * 밴드의 활성 여부를 표현하며,
 * 실제 삭제 대신 {@code INACTIVE} 상태로 전환하는
 * 소프트 삭제(soft delete) 방식을 사용합니다.
 * </p>
 *
 * <ul>
 *   <li>{@link #ACTIVE} - 정상적으로 활동 중인 밴드</li>
 *   <li>{@link #INACTIVE} - 비활성화된 밴드(삭제 처리에 해당)</li>
 * </ul>
 *
 * <p>
 * 기본 조회 대상은 {@code ACTIVE} 상태의 밴드이며,
 * {@code INACTIVE} 상태의 밴드는 사용자에게 노출되지 않습니다.
 * </p>
 *
 * @since 1.0.0
 */
public enum BandStatus {

    /** 정상적으로 활동 중인 밴드 */
    ACTIVE,

    /** 비활성화된 밴드 (소프트 삭제 상태) */
    INACTIVE
}
