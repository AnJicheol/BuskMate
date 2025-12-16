package org.example.buskmate.band.domain;

/**
 * 모집 글 지원(Recruit Application)의 상태를 나타내는 열거형입니다.
 *
 * <p>
 * {@code RecruitApplicationStatus}는 사용자가 모집 글에 지원한 이후
 * 해당 지원이 어떤 처리 단계에 있는지를 표현합니다.
 * </p>
 *
 * <h3>상태 흐름</h3>
 * <pre>
 * WAITING
 *   ├─ ACCEPTED
 *   ├─ REJECTED
 *   └─ DELETED
 * </pre>
 *
 * <p>
 * 모든 상태 변경은 실제 데이터 삭제가 아닌
 * 상태 값 변경을 통해 관리됩니다.
 * </p>
 */
public enum RecruitApplicationStatus {
    WAITING,
    ACCEPTED,
    REJECTED,
    DELETED
}
