package org.example.buskmate.map.domain;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;


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

    MapLocation(double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
    }

    public static MapLocation of(double lat, double lng) {
        return new MapLocation(lat, lng);
    }


}
