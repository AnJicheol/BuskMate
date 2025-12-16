package org.example.buskmate.band.repository;

import lombok.Getter;

@Getter
public class postDto {

    private String bandId;

    /**
     * 밴드의 이름입니다.
     * <p>
     * 최대 60자까지 입력 가능하며, 필수 입력 항목입니다.
     */

    private String name;

    /**
     * 밴드장(리더)의 사용자 식별자입니다.
     * <p>
     * 사용자 도메인의 외부 식별자를 참조합니다.
     * 이 값은 밴드 생성 시 설정되며, 변경이 필요한 경우 별도의 도메인 이벤트를 통해 처리해야 합니다.
     */

    private String leaderId;

}