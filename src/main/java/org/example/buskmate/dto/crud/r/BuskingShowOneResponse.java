package org.example.buskmate.dto.crud.r;

public record BuskingShowOneResponse(
        String buskingId,
        String name
)
{
    public static BuskingShowOneResponse of(
            String buskingId,
            String name
    )
    {
        return new BuskingShowOneResponse(buskingId, name);
    }
}
