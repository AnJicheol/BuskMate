package org.example.buskmate.band.dto.bandmember;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 밴드 멤버 등록을 위한 요청 DTO입니다.
 *
 * <p>
 * 밴드에 새 멤버를 초대하거나 등록하기 위해
 * 사용자 식별자 정보를 전달하는 용도로 사용됩니다.
 * </p>
 *
 * @since 1.0.0
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BandMemberRegisterRequest {

    @NotBlank
    private String userId;
}
