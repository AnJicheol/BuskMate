package org.example.buskmate.dto.crud.u;

import java.time.LocalDateTime;

public record BuskingEditRequest(
        String buskingId,
        String title,
        LocalDateTime buskingStart,
        LocalDateTime buskingEnd
)
{ }
