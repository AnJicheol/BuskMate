package org.example.buskmate.band.dto.recruitpost;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.buskmate.band.domain.RecruitPostStatus;

import java.time.LocalDateTime;
/**
 * 모집 글 목록 조회 시 사용되는 DTO입니다.
 *
 * <p>
 * {@code RecruitPostListDto}는 모집 글 목록 화면에 표시하기 위한
 * 요약 정보를 제공하며,
 * 다수의 모집 글을 효율적으로 조회할 때 사용됩니다.
 * </p>
 *
 * <h3>사용 목적</h3>
 * <ul>
 *   <li>활성 상태 모집 글 목록 조회</li>
 *   <li>모집 글 리스트 화면 구성</li>
 * </ul>
 *
 * <p>
 * 상세 내용({@code content})은 포함하지 않으며,
 * 목록 조회에 필요한 최소한의 정보만을 제공합니다.
 * </p>
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor

public class RecruitPostListDto {
    private String postId;
    private String bandId;
    private String title;
    private RecruitPostStatus status;
    private LocalDateTime createdAt;
}
