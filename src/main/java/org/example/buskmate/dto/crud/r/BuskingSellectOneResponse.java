package org.example.buskmate.dto.crud.r;

public record BuskingSellectOneResponse(
        String buskingId,
        String title,
        String place,
        String buskingStart,
        String buskingEnd
)
{
    public static BuskingSellectOneResponse of(
            String buskingId,
            String title,
            String place,
            String buskingStart,
            String buskingEnd
    )
    {
        return new BuskingSellectOneResponse(
                buskingId,
                title,
                place,
                buskingStart,
                buskingEnd
        );
    }
}
