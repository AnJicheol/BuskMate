package org.example.buskmate.dto.crud.c;

import java.time.LocalDateTime;

public record BuskingCreateRequest(
        String buskingId,
        String title,
        String place,
        LocalDateTime buskingStart,
        LocalDateTime buskingEnd
) { }
