    package org.example.buskmate.map.domain;

    import jakarta.persistence.*;
    import lombok.Getter;
    import lombok.NoArgsConstructor;

    import java.time.LocalDateTime;

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
