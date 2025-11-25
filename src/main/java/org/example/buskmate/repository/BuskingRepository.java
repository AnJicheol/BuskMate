package org.example.buskmate.repository;

import org.example.buskmate.domain.Busking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BuskingRepository extends JpaRepository<Busking, String> {

    // 1. 전체 조회
    @Query("""
        select b
        from Busking b
        where b.buskingStart <= CURRENT_TIMESTAMP
            and b.buskingEnd >= CURRENT_TIMESTAMP
    """)
    List<Busking> findAllBusking();

    // 2. 단일 조회
    @Query("""
        select b
        from Busking b
        where b.buskingId = :buskingId
    """)
    Optional<Busking> findOneBusking(@Param("buskingId") String buskingId);
}
