package org.example.buskmate.busking.domain;

public enum BuskingStatus {
    PLANNED, // 기획만 한 상태
    OPEN,    // 공개 + 신청가능
    RESERVED,// 밴드가 선점한 상태
    CANCELED,// 취소됨
    FINISHED;// 공연 종료
}
