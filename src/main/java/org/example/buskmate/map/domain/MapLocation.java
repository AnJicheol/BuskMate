package org.example.buskmate.map.domain;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;


/**
 * 지도 마커의 좌표(위도/경도)를 표현하는 엔티티
 * - MapMarker와 1:1 관계로 연결된다.
 */

@Entity
@Table(name = "map_location")
@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class MapLocation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "lat", nullable = false)
    private double lat;

    @Column(name = "lng", nullable = false)
    private double lng;

    /**
     * 위도/경도로 MapLocation을 생성한다. (패키지 범위 생성자)
     */
    MapLocation(double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
    }

    /**
     * 정적 팩토리 메서드로 MapLocation을 생성한다.
     */
    public static MapLocation of(double lat, double lng) {
        return new MapLocation(lat, lng);
    }


}
