package org.example.buskmate.band.repository;

import org.example.buskmate.band.domain.BandMember;
import org.example.buskmate.band.domain.BandMemberStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BandMemberRepository extends JpaRepository<BandMember, Long> {

    List<BandMember> findAllByBand_BandIdAndStatus(String bandId, BandMemberStatus status);

    // 1. 이미 ACTIVE 멤버인지 체크
    boolean existsByBand_BandIdAndUserIdAndStatus(
            String bandId,
            String userId,
            BandMemberStatus status
    );

    // 2. 초대 대기(INVITED) 상태인 멤버 조회 (수락/거절할 때 사용)
    Optional<BandMember> findByBand_BandIdAndUserId(
            String bandId,
            String userId
    );
}