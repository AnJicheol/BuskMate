package org.example.buskmate.band.dto.recruitpost;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 모집 글 수정 요청 시 사용되는 DTO입니다.
 *
 * <p>
 * {@code UpdateRecruitPostRequestDto}는
 * 기존에 작성된 모집 글의 제목과 내용을
 * 수정하기 위해 클라이언트가 전달하는 요청 객체입니다.
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
 *   <li>title: 필수 값, 최대 50자</li>
 *   <li>content: 필수 값, 최대 2000자</li>
 * </ul>
 *
 * <p>
 * 이 DTO는 수정 요청 전용 객체로,
 * 비즈니스 로직이나 상태 변경 책임을 가지지 않습니다.
 * </p>
 */
@Getter
@NoArgsConstructor
public class UpdateRecruitPostRequestDto {
    @NotBlank
    @Size(max = 50)
    private String title;
    @NotBlank
    @Size(max = 2000)
    private String content;
}
