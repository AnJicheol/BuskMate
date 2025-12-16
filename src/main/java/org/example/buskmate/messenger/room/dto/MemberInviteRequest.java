package org.example.buskmate.messenger.room.dto;

import jakarta.validation.constraints.NotBlank;

public record MemberInviteRequest(
        @NotBlank String memberId
) {}
