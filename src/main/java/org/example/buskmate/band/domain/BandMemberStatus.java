package org.example.buskmate.band.domain;

/**
 * 밴드 멤버의 참여 상태를 나타내는 열거형입니다.
 *
 * <p>
 * 밴드 초대부터 활동, 거절 및 추방까지
 * 밴드 멤버의 참여 흐름을 상태 값으로 표현합니다.
 * </p>
 *
 * @since 1.0.0
 */
public enum BandMemberStatus {

    /** 밴드에 초대된 상태 (수락/거절 대기) */
    INVITED,

    /** 밴드에 정상적으로 소속되어 활동 중인 상태 */
    ACTIVE,

    /** 밴드 초대를 거절한 상태 */
    REJECTED,

    /** 밴드에서 추방된 상태 */
    KICKED
}
