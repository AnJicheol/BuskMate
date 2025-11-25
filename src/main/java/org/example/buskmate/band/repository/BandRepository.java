package org.example.buskmate.band.repository;

import org.example.buskmate.band.domain.Band;
import org.example.buskmate.band.domain.BandStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 밴드(Band) 엔티티에 대한 데이터 접근을 처리하는 리포지토리 인터페이스입니다.
 * <p>
 * 이 인터페이스는 Spring Data JPA의 {@link JpaRepository}를 상속받아 기본적인 CRUD 작업을 제공하며,
 * 밴드 도메인에 특화된 쿼리 메서드를 정의합니다.
 * </p>
 *
 * <h3>주요 기능</h3>
 * <ul>
 *   <li>기본 CRUD 작업 (상속됨)</li>
 *   <li>밴드 ID와 상태로 조회</li>
 *   <li>상태별 밴드 목록 조회</li>
 * </ul>
 *
 * @see Band
 * @see BandStatus
 * @since 1.0.0
 */
public interface BandRepository extends JpaRepository<Band, Long> {

    /**
     * 주어진 밴드 ID와 상태에 해당하는 밴드를 조회합니다.
     *
     * @param bandId 조회할 밴드의 외부 식별자(ULID)
     * @param status 조회할 밴드의 상태 (ACTIVE/INACTIVE)
     * @return 일치하는 밴드 엔티티. 없을 경우 null 반환
     * @throws IllegalArgumentException bandId가 null이거나 빈 문자열인 경우
     * @see Band#getBandId()
     * @see BandStatus
     */
    Band findByBandIdAndStatus(String bandId, BandStatus status);

    /**
     * 지정된 상태를 가진 모든 밴드 목록을 조회합니다.
     * <p>
     * 주로 활성화된(ACTIVE) 밴드 목록을 조회하는 데 사용됩니다.
     *
     * @param status 조회할 밴드의 상태
     * @return 지정된 상태의 밴드 목록. 없을 경우 빈 리스트 반환
     * @see BandStatus#ACTIVE
     * @see BandStatus#INACTIVE
     */
    List<Band> findAllByStatus(BandStatus status);
}