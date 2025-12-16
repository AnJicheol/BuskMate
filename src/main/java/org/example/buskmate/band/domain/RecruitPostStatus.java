package org.example.buskmate.band.domain;
/**
 * 모집 글(Recruit Post)의 상태를 나타내는 열거형입니다.
 *
 * <p>
 * {@code RecruitPostStatus}는 모집 글이 현재
 * 모집 중인지, 마감되었는지, 또는 삭제(비활성화)되었는지를 표현합니다.
 * </p>
 *
 * <h3>상태 흐름</h3>
 * <pre>
 * OPEN
 *   ├─ CLOSED
 *   └─ DELETED
 * </pre>
 *
 * <p>
 * 모든 상태 변경은 실제 데이터 삭제가 아닌
 * 상태 값 변경을 통해 관리됩니다.
 * </p>
 */
public enum RecruitPostStatus {
    OPEN,
    CLOSED,
    DELETED
}
