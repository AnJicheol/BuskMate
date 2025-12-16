package org.example.buskmate.band.dto.recruitpost;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
/**
 * 모집 글 생성 요청 시 사용되는 DTO입니다.
 *
 * <p>
 * {@code CreateRecruitPostRequestDto}는 클라이언트가
 * 새로운 모집 글을 생성하기 위해 전달해야 하는
 * 필수 입력 값을 정의합니다.
 * </p>
 *
 * <p>
 * 입력 값에 대한 기본적인 유효성 검증은
 * Bean Validation 어노테이션을 통해
 * 컨트롤러 레벨에서 수행됩니다.
 * </p>
 *
 * <h3>유효성 검증 규칙</h3>
 * <ul>
 *   <li>bandId: 필수 값</li>
 *   <li>title: 필수 값, 최대 50자</li>
 *   <li>content: 필수 값, 최대 2000자</li>
 * </ul>
 *
 * <p>
 * 이 DTO는 요청 전용 객체로,
 * 비즈니스 로직이나 상태 변경 책임을 가지지 않습니다.
 * </p>
 */
@Getter
@NoArgsConstructor
public class CreateRecruitPostRequestDto {
    @NotBlank
    private String bandId;

    @NotBlank
    @Size(max = 50)
    private String title;

    @NotBlank
    @Size(max = 2000)
    private String content;

}
