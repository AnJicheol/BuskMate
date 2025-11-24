package org.example.buskmate.domain;

import lombok.Generated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter

@NoArgsConstructor
public class Busking {

    @Id
    @Generated(strategy = Generation.IDENTITY)
    private id;

    @Column(nullable=false, name="busking_id, length=26")
    private String buskingId;

    private String name;
}
