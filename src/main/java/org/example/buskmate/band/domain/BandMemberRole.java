package org.example.buskmate.band.domain;

/**
 * 밴드 내 멤버의 역할을 정의하는 열거형입니다.
 *
 * <p>
 * 밴드에 소속된 사용자가 수행할 수 있는 역할을 나타내며,
 * 권한 판단 및 비즈니스 로직에서 사용됩니다.
 * </p>
 *
 * @since 1.0.0
 */
public enum BandMemberRole {

    /** 밴드를 생성하고 관리하는 리더 */
    LEADER,

    /** 일반 밴드 멤버 */
    MEMBER
}
