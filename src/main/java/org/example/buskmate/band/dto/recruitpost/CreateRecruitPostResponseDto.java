package org.example.buskmate.band.dto.recruitpost;

import lombok.AllArgsConstructor;
import lombok.Getter;
/**
 * 모집 글 생성 요청에 대한 응답 DTO입니다.
 *
 * <p>
 * {@code CreateRecruitPostResponseDto}는
 * 새로운 모집 글이 성공적으로 생성된 후
 * 클라이언트에 반환되는 기본 응답 객체입니다.
 * </p>
 *
 * <h3>사용 목적</h3>
 * <ul>
 *   <li>생성된 모집 글의 식별자 전달</li>
 *   <li>모집 글이 생성된 밴드 정보 확인</li>
 *   <li>생성된 모집 글 제목 확인</li>
 * </ul>
 *
 * <p>
 * 상세 내용이 아닌,
 * 생성 결과 확인에 필요한 최소 정보만을 포함합니다.
 * </p>
 */
@Getter
@AllArgsConstructor
public class CreateRecruitPostResponseDto {
    private String postId;
    private String bandId;
    private String title;
}
