package org.example.buskmate.dto.crud.u;

public record BuskingEditRequest(
        String buskingId,
        String title,
        String place,
        String buskingStart,
        String buskingEnd
)
{ }
