package org.example.buskmate.band.dto.bandmember;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BandMemberRegisterRequest {
    @NotBlank
    private String userId;
}