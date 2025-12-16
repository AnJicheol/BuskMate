package org.example.buskmate.band.dto.recruitpost;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.buskmate.band.domain.RecruitPostStatus;

import java.time.LocalDateTime;
/**
 * 모집 글 상세 조회 시 반환되는 응답 DTO입니다.
 *
 * <p>
 * {@code RecruitPostDetailResponseDto}는
 * 특정 모집 글의 상세 정보를 클라이언트에 전달하기 위해 사용되며,
 * 제목, 내용, 상태, 생성 시각 등의 핵심 정보를 포함합니다.
 * </p>
 *
 * <h3>사용 시점</h3>
 * <ul>
 *   <li>모집 글 상세 조회 API</li>
 *   <li>모집 글 생성/수정 이후 결과 확인</li>
 * </ul>
 *
 * <p>
 * 목록 조회용 DTO와 분리하여,
 * 상세 화면에 필요한 정보만을 명확하게 제공합니다.
 * </p>
 */
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RecruitPostDetailResponseDto {
    private String postId;
    private String bandId;
    private String title;
    private String content;
    private RecruitPostStatus status;
    private LocalDateTime createdAt;

}
