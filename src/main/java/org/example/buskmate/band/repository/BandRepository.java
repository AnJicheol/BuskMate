package org.example.buskmate.band.repository;

import org.example.buskmate.band.domain.Band;
import org.example.buskmate.band.domain.BandStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 밴드(Band) 엔티티에 대한 데이터 접근을 담당하는 레포지토리입니다.
 *
 * <p>
 * Spring Data JPA의 {@link JpaRepository}를 기반으로
 * 밴드 조회 및 상태 기반 검색 기능을 제공합니다.
 * </p>
 *
 * @see Band
 * @see BandStatus
 * @since 1.0.0
 */
public interface BandRepository extends JpaRepository<Band, Long> {

    /**
     * 밴드 ID와 상태로 밴드를 조회합니다.
     *
     * <p>
     * 주로 활성 상태({@link BandStatus#ACTIVE})의
     * 단일 밴드를 조회할 때 사용됩니다.
     * </p>
     *
     * @param bandId 밴드의 외부 식별자(ULID)
     * @param status 밴드 상태
     * @return 조건에 맞는 밴드 엔티티
     */
    Band findByBandIdAndStatus(String bandId, BandStatus status);

    /**
     * 지정한 상태의 모든 밴드 목록을 조회합니다.
     *
     * @param status 밴드 상태
     * @return 해당 상태를 가진 밴드 목록
     */
    List<Band> findAllByStatus(BandStatus status);
}
