package org.example.buskmate.dto.crud.d;

public record BuskingDeleteResponse(
        String result
)
{
    public static BuskingDeleteResponse of(
            String result
    )
    {
        return new BuskingDeleteResponse(result);
    }
}
