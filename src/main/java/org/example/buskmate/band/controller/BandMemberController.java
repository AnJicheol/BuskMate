package org.example.buskmate.band.controller;

import lombok.RequiredArgsConstructor;
import org.example.buskmate.band.dto.bandmember.BandMemberListItemResponse;
import org.example.buskmate.band.dto.bandmember.BandMemberRegisterRequest;
import org.example.buskmate.band.service.BandMemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/bands")
@Tag(name = "Band Member API", description = "밴드 멤버 조회/초대/승인/거절/추방 API")
public class BandMemberController {

    private final BandMemberService bandMemberService;

    @Operation(
            summary = "밴드 멤버 목록 조회",
            description = "bandId에 해당하는 밴드의 멤버 목록을 조회합니다."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "멤버 목록 조회 성공",
                    content = @Content(schema = @Schema(implementation = BandMemberListItemResponse.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "해당 bandId의 밴드가 존재하지 않음",
                    content = @Content
            )
    })
    @GetMapping("/{bandId}/members")
    public ResponseEntity<List<BandMemberListItemResponse>> getMembers(
            @Parameter(description = "밴드 외부 식별자", example = "01JH1ABCDXYZ...")
            @PathVariable String bandId
    ) {
        List<BandMemberListItemResponse> members = bandMemberService.getMembers(bandId);
        return ResponseEntity.ok(members);
    }

    @Operation(
            summary = "밴드 멤버 초대",
            description = "밴드 리더가 다른 유저를 밴드 멤버로 초대합니다."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "초대 성공 (응답 본문 없음)"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "요청 값 검증 실패 (예: 잘못된 userId 형식)",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "밴드 리더가 아닌 사용자가 초대 시도",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "밴드 또는 유저가 존재하지 않음",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "이미 멤버이거나 대기중인 초대가 존재함",
                    content = @Content
            )
    })
    @PostMapping("/{bandId}/members/invite")
    public ResponseEntity<Void> inviteMember(
            @Parameter(description = "밴드 외부 식별자", example = "01JH1ABCDXYZ...")
            @PathVariable String bandId,
            @Parameter(hidden = true)
            @AuthenticationPrincipal CustomUser leader,
            @RequestBody BandMemberRegisterRequest request
    ) {
        bandMemberService.inviteMember(
                bandId,
                leader.getUserId(),
                request.getUserId()
        );
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "밴드 초대 수락",
            description = "현재 로그인한 사용자가 특정 밴드의 초대를 수락합니다."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "초대 수락 성공 (응답 본문 없음)"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "해당 밴드의 초대 대상이 아닌 경우",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "밴드 또는 초대가 존재하지 않음",
                    content = @Content
            )
    })
    @PostMapping("/{bandId}/members/invitations/accept")
    public ResponseEntity<Void> acceptInvitation(
            @Parameter(description = "밴드 외부 식별자", example = "01JH1ABCDXYZ...")
            @PathVariable String bandId,
            @Parameter(hidden = true)
            @AuthenticationPrincipal CustomUser user
    ) {
        bandMemberService.acceptInvitation(bandId, user.getUserId());
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "밴드 초대 거절",
            description = "현재 로그인한 사용자가 특정 밴드의 초대를 거절합니다."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "초대 거절 성공 (응답 본문 없음)"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "해당 밴드의 초대 대상이 아닌 경우",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "밴드 또는 초대가 존재하지 않음",
                    content = @Content
            )
    })
    @PostMapping("/{bandId}/members/invitations/reject")
    public ResponseEntity<Void> rejectInvitation(
            @Parameter(description = "밴드 외부 식별자", example = "01JH1ABCDXYZ...")
            @PathVariable String bandId,
            @Parameter(hidden = true)
            @AuthenticationPrincipal CustomUser user
    ) {
        bandMemberService.rejectInvitation(bandId, user.getUserId());
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "밴드 멤버 추방",
            description = "밴드 리더가 특정 멤버를 밴드에서 추방합니다."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "멤버 추방 성공 (응답 본문 없음)"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "밴드 리더가 아닌 사용자가 추방 시도",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "밴드 또는 대상 유저가 존재하지 않음",
                    content = @Content
            )
    })
    @DeleteMapping("/{bandId}/members/{targetUserId}")
    public ResponseEntity<Void> kickMember(
            @Parameter(description = "밴드 외부 식별자", example = "01JH1ABCDXYZ...")
            @PathVariable String bandId,
            @Parameter(description = "추방 대상 유저 ID", example = "user-123")
            @PathVariable String targetUserId,
            @Parameter(hidden = true)
            @AuthenticationPrincipal CustomUser leader
    ) {
        bandMemberService.kickMember(bandId, leader.getUserId(), targetUserId);
        return ResponseEntity.noContent().build();
    }
}