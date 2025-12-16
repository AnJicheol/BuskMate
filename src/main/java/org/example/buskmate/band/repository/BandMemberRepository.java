package org.example.buskmate.band.repository;

import org.example.buskmate.band.domain.BandMember;
import org.example.buskmate.band.domain.BandMemberStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * 밴드 멤버 엔티티에 대한 데이터 접근을 담당하는 레포지토리입니다.
 *
 * <p>
 * 특정 밴드에 소속된 멤버 조회, 멤버 존재 여부 확인,
 * 초대 상태 멤버 조회 등 밴드 멤버십 관련 조회 기능을 제공합니다.
 * </p>
 *
 * @since 1.0.0
 */
public interface BandMemberRepository extends JpaRepository<BandMember, Long> {

    /**
     * 특정 밴드에 소속된 멤버 중,
     * 지정한 상태의 멤버 목록을 조회합니다.
     */
    List<BandMember> findAllByBand_BandIdAndStatus(String bandId, BandMemberStatus status);

    /**
     * 특정 밴드에 대해,
     * 해당 사용자가 지정한 상태의 멤버로 이미 존재하는지 확인합니다.
     */
    boolean existsByBand_BandIdAndUserIdAndStatus(
            String bandId,
            String userId,
            BandMemberStatus status
    );

    /**
     * 특정 밴드에서 사용자 ID로 멤버를 조회합니다.
     *
     * <p>
     * 주로 초대 상태(INVITED)의 멤버를
     * 수락 또는 거절 처리할 때 사용됩니다.
     * </p>
     */
    Optional<BandMember> findByBand_BandIdAndUserId(
            String bandId,
            String userId
    );

    /**
     * 특정 밴드에 해당 사용자가
     * 멤버로 존재하는지 여부를 확인합니다.
     */
    boolean existsByBand_BandIdAndUserId(String bandId, String userId);
}
