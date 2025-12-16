package org.example.buskmate.band.dto.recruitpost;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.example.buskmate.band.domain.RecruitPostStatus;
/**
 * 모집 글 상태 변경 요청에 대한 응답 DTO입니다.
 *
 * <p>
 * {@code RecruitPostStatusResponseDto}는
 * 모집 글 마감 또는 삭제(비활성화)와 같이
 * 모집 글의 상태가 변경된 이후,
 * 변경 결과를 클라이언트에 전달하기 위해 사용됩니다.
 * </p>
 *
 * <h3>사용 시점</h3>
 * <ul>
 *   <li>모집 글 마감 처리</li>
 *   <li>모집 글 삭제(비활성화) 처리</li>
 * </ul>
 *
 * <p>
 * 상태 변경 결과 확인에 필요한 최소 정보만 포함하며,
 * 상세 정보는 별도의 상세 조회 API를 통해 확인합니다.
 * </p>
 */
@Getter
@Builder
@AllArgsConstructor
public class RecruitPostStatusResponseDto {
    private String postId;
    private RecruitPostStatus status;
}
