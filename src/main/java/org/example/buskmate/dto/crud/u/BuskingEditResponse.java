package org.example.buskmate.dto.crud.u;

public record BuskingEditResponse(
        String title,
        String place,
        String buskingStart,
        String buskingEnd
)
{
    public static BuskingEditResponse of(
            String title,
            String place,
            String buskingStart,
            String buskingEnd
    )
    {
        return new BuskingEditResponse(
                title,
                place,
                buskingStart,
                buskingEnd
        );
    }
}
