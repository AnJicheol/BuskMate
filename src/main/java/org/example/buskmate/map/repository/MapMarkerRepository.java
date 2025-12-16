package org.example.buskmate.map.repository;

import org.example.buskmate.map.domain.MapMarker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * 지도 마커 JPA 레포지토리
 * - bounds(사각 영역) 기반 마커 조회를 위한 커스텀 쿼리를 제공한다.
 */
public interface MapMarkerRepository extends JpaRepository<MapMarker, Long> {

    /**
     * 주어진 bounds(위도/경도 범위) 안에 포함되는 마커를 조회한다.
     */
    @Query("""
        select m
        from MapMarker m
        where m.location.lat between :southLat and :northLat
          and m.location.lng between :westLng and :eastLng
        """)
    List<MapMarker> findInBounds(
            @Param("southLat") double southLat,
            @Param("northLat") double northLat,
            @Param("westLng") double westLng,
            @Param("eastLng") double eastLng
    );
}
