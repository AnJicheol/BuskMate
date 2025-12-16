package org.example.buskmate.band.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.example.buskmate.auth.dto.UsersPrincipal;
import org.example.buskmate.band.dto.CustomUser;
import org.example.buskmate.band.dto.recruitpost.*;
import org.example.buskmate.band.service.RecruitPostService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 모집 글(Recruit Post)과 관련된 API 요청을 처리하는 컨트롤러입니다.
 *
 * <p>
 * 밴드 리더는 팀원 모집을 위한 모집 글을 생성, 수정, 마감, 삭제할 수 있으며,
 * 사용자는 활성 상태의 모집 글 목록을 조회하고
 * 모집 글 상세 정보를 확인할 수 있습니다.
 * </p>
 *
 * <h3>주요 기능</h3>
 * <ul>
 *   <li>모집 글 생성</li>
 *   <li>모집 글 상세 조회</li>
 *   <li>활성 상태 모집 글 목록 조회</li>
 *   <li>모집 글 수정</li>
 *   <li>모집 글 마감</li>
 *   <li>모집 글 삭제(비활성화)</li>
 * </ul>
 *
 * <p>
 * 모집 글에 대한 수정/상태 변경 API는
 * 해당 모집 글이 속한 밴드의 리더만 호출할 수 있습니다.
 * </p>
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/recruit")
public class RecruitPostController {
    private final RecruitPostService recruitPostService;


    /**
     * 로그인한 사용자가 새로운 팀원 모집 글을 생성합니다.
     *
     * <p>
     * 해당 모집 글은 사용자가 리더로 속한 밴드를 기준으로 생성되며,
     * 밴드 리더만 모집 글을 작성할 수 있습니다.
     * </p>
     *
     * @param req  모집 글 생성 요청 정보
     * @param user 인증된 사용자 정보
     * @return 생성된 모집 글의 기본 정보
     */
    @Operation(
            summary = "모집 글 생성",
            description = "로그인한 사용자가 새로운 팀원 모집 글을 생성합니다."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "생성 성공",
                    content = @Content(schema = @Schema(implementation = CreateRecruitPostResponseDto.class))
            ),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 데이터"),
            @ApiResponse(responseCode = "401", description = "인증 필요"),
            @ApiResponse(responseCode = "404", description = "밴드를 찾을 수 없음"),
            @ApiResponse(responseCode = "403", description = "해당 밴드 리더가 아님")
    })
    @PostMapping
    public ResponseEntity<CreateRecruitPostResponseDto> createRecruitPost(@RequestBody CreateRecruitPostRequestDto req, @AuthenticationPrincipal UsersPrincipal user) {
        return ResponseEntity.ok(
                recruitPostService.create(req, user.getUserId()));
    }


    /**
     * 모집 글 ID를 기준으로 모집 글의 상세 정보를 조회합니다.
     *
     * @param postId 조회할 모집 글 식별자
     * @return 모집 글 상세 정보
     */
    @Operation(
            summary = "모집 글 상세 조회",
            description = "모집 글 ID로 상세 정보를 조회합니다."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "조회 성공",
                    content = @Content(schema = @Schema(implementation = RecruitPostDetailResponseDto.class))
            ),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 모집 글")
    })
    @GetMapping("/{postId}")
    public ResponseEntity<RecruitPostDetailResponseDto> getDetail(@PathVariable String postId){
        return ResponseEntity.ok(
                recruitPostService.getDetail(postId));
    }


    /**
     * 현재 모집 중인(활성 상태) 모집 글 목록을 조회합니다.
     *
     * <p>
     * 비회원 또는 로그인하지 않은 사용자도
     * 호출할 수 있는 공개 API입니다.
     * </p>
     *
     * @return 활성 상태의 모집 글 목록
     */
    @Operation(
            summary = "활성 상태 모집 글 목록 조회",
            description = "현재 모집 중(활성 상태)인 모집 글 목록을 조회합니다."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "조회 성공",
                    content = @Content(
                            array = @ArraySchema(schema = @Schema(implementation = RecruitPostListDto.class))
                    )
            )
    })
    @GetMapping("/list/active")
    public ResponseEntity<List<RecruitPostListDto>> getActiveList(){
        return ResponseEntity.ok(
                recruitPostService.getActiveList());
    }

    /**
     * 모집 글 작성자(밴드 리더)가 모집 글 내용을 수정합니다.
     *
     * <p>
     * 제목, 내용, 모집 조건 등
     * 모집 글의 주요 정보를 수정할 수 있으며,
     * 작성자가 아닌 경우 수정이 거부됩니다.
     * </p>
     *
     * @param postId 수정할 모집 글 식별자
     * @param req    모집 글 수정 요청 정보
     * @param user   인증된 사용자 정보
     * @return 수정된 모집 글 상세 정보
     */
    @Operation(
            summary = "모집 글 수정",
            description = "모집 글 작성자(밴드 리더)가 제목/내용/모집 조건 등을 수정합니다."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "수정 성공",
                    content = @Content(schema = @Schema(implementation = RecruitPostDetailResponseDto.class))
            ),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 데이터"),
            @ApiResponse(responseCode = "401", description = "인증 필요"),
            @ApiResponse(responseCode = "403", description = "해당 모집 글을 수정할 권한 없음"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 모집 글")
    })
    @PatchMapping("/{postId}")
    public ResponseEntity<RecruitPostDetailResponseDto> updateRecruitPost(@PathVariable String postId, @RequestBody UpdateRecruitPostRequestDto req,@AuthenticationPrincipal UsersPrincipal user){
        return ResponseEntity.ok(
                recruitPostService.update(postId, req, user.getUserId()));
    }


    /**
     * 밴드 리더가 모집 글을 마감 상태로 변경합니다.
     *
     * <p>
     * 마감된 모집 글은 더 이상 지원을 받을 수 없으며,
     * 상태는 CLOSED로 변경됩니다.
     * </p>
     *
     * @param postId 마감할 모집 글 식별자
     * @param user   인증된 사용자 정보
     * @return 변경된 모집 글 상태 정보
     */
    @Operation(
            summary = "모집 글 마감",
            description = "밴드 리더가 더 이상 지원을 받지 않도록 모집 글 상태를 마감으로 변경합니다."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "마감 상태 변경 성공",
                    content = @Content(schema = @Schema(implementation = RecruitPostStatusResponseDto.class))
            ),
            @ApiResponse(responseCode = "401", description = "인증 필요"),
            @ApiResponse(responseCode = "403", description = "해당 모집 글을 마감할 권한 없음"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 모집 글")
    })
    @PatchMapping("/{postId}/close")
    public ResponseEntity<RecruitPostStatusResponseDto> closeRecruitPost(@PathVariable String postId, @AuthenticationPrincipal UsersPrincipal user){
        return ResponseEntity.ok(
                recruitPostService.close(postId, user.getUserId()));
    }

    /**
     * 밴드 리더가 모집 글을 삭제(비활성화) 처리합니다.
     *
     * <p>
     * 실제 데이터 삭제가 아닌,
     * 모집 글 상태를 변경하여 더 이상 노출되지 않도록 처리할 수 있습니다.
     * </p>
     *
     * @param postId 삭제(비활성화)할 모집 글 식별자
     * @param user   인증된 사용자 정보
     * @return 변경된 모집 글 상태 정보
     */
    @Operation(
            summary = "모집 글 삭제(비활성화)",
            description = "밴드 리더가 모집 글을 삭제(또는 비활성화)합니다. " +
                    "실제 물리 삭제가 아닌 상태 변경일 수 있습니다."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "삭제(비활성화) 상태 변경 성공",
                    content = @Content(schema = @Schema(implementation = RecruitPostStatusResponseDto.class))
            ),
            @ApiResponse(responseCode = "401", description = "인증 필요"),
            @ApiResponse(responseCode = "403", description = "해당 모집 글을 삭제할 권한 없음"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 모집 글")
    })
    @PatchMapping("/{postId}/delete")
    public ResponseEntity<RecruitPostStatusResponseDto> deleteRecruitPost(@PathVariable String postId, @AuthenticationPrincipal UsersPrincipal user){
        return ResponseEntity.ok(
                recruitPostService.delete(postId, user.getUserId()));
    }
}
