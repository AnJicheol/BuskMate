package org.example.buskmate.dto.crud.u;

import org.example.buskmate.domain.Busking;

import java.time.LocalDateTime;

public record BuskingEditResponse(
        String title,
        LocalDateTime buskingStart,
        LocalDateTime buskingEnd
)
{
    public static BuskingEditResponse of(Busking b)
    {
        return new BuskingEditResponse(
                b.getTitle(),
                b.getBuskingStart(),
                b.getBuskingEnd()
        );
    }
}
