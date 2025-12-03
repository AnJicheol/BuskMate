package org.example.buskmate.map.repository;

import org.example.buskmate.map.domain.MapMarker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MapMarkerRepository extends JpaRepository<MapMarker, Long> {

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
