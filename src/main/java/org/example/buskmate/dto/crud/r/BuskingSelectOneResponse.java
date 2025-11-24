package org.example.buskmate.dto.crud.r;

public record BuskingSelectOneResponse(
        String buskingId,
        String title,
        String place,
        String buskingStart,
        String buskingEnd
)
{
    public static BuskingSelectOneResponse of(
            String buskingId,
            String title,
            String place,
            String buskingStart,
            String buskingEnd
    )
    {
        return new BuskingSelectOneResponse(
                buskingId,
                title,
                place,
                buskingStart,
                buskingEnd
        );
    }
}
