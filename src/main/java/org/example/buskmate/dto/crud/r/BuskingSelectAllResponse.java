package org.example.buskmate.dto.crud.r;

public record BuskingSelectAllResponse(
        String buskingId,
        String title,
        String place,
        String buskingStart,
        String buskingEnd
) {
    public static BuskingSelectAllResponse of(
            String buskingId,
            String title,
            String place,
            String buskingStart,
            String buskingEnd
    )
    {
        return new BuskingSelectAllResponse(
                buskingId,
                title,
                place,
                buskingStart,
                buskingEnd
        );
    }
}
