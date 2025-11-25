package org.example.buskmate.dto.crud.c;

import java.time.LocalDateTime;

public record BuskingCreateResponse(
        String buskingId,
        String title,
        LocalDateTime buskingStart,
        LocalDateTime buskingEnd
)
{
    public static BuskingCreateResponse of(
            String buskingId,
            String title,
            LocalDateTime buskingStart,
            LocalDateTime buskingEnd
    )
    {
        return new BuskingCreateResponse(
                buskingId,
                title,
                buskingStart,
                buskingEnd
        );
    }
}
