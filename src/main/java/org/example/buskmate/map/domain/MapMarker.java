    package org.example.buskmate.map.domain;

    import jakarta.persistence.*;
    import lombok.Getter;
    import lombok.NoArgsConstructor;

    import java.time.LocalDateTime;

    /**
     * 지도에 표시되는 마커 엔티티
     * - 마커 타입(BAND/BUSKING)과 위치(MapLocation), 제목/요약 정보를 가진다.
     */
    @Entity
    @Table(name = "map_marker")
    @NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
    @Getter
    public class MapMarker {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;


        @Enumerated(EnumType.STRING)
        @Column(name = "marker_type", nullable = false)
        private MarkerType markerType;

        @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = false)
        @JoinColumn(name = "map_location_id", nullable = false, unique = true)
        private MapLocation location;


        @Column(nullable = false)
        private String title;

        private String summary;

        @Column(nullable = false, updatable = false)
        private LocalDateTime createdAt;

        /**
         * 마커 타입/위치/제목/요약을 기반으로 마커를 생성한다. (패키지 범위 생성자)
         */
        MapMarker(
                MarkerType markerType,
                MapLocation location,
                String title,
                String summary
        ) {
            this.markerType = markerType;
            this.location = location;
            this.title = title;
            this.summary = summary;
        }

        /**
         * 정적 팩토리 메서드로 마커 엔티티를 생성한다.
         */
        public static MapMarker of(
                MarkerType markerType,
                MapLocation location,
                String title,
                String summary
        ) {
            return new MapMarker(markerType, location, title, summary);
        }


        @PrePersist
        void onPrePersist() {
            this.createdAt = LocalDateTime.now();
        }
    }
