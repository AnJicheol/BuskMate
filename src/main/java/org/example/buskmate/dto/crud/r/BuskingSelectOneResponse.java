package org.example.buskmate.dto.crud.r;

import org.example.buskmate.domain.Busking;

import java.time.LocalDateTime;

public record BuskingSelectOneResponse(
        String title,
        LocalDateTime buskingStart,
        LocalDateTime buskingEnd
)
{
    public static BuskingSelectOneResponse of(Busking b)
    {
        return new BuskingSelectOneResponse(
                b.getTitle(),
                b.getBuskingStart(),
                b.getBuskingEnd()
        );
    }
}
