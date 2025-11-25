package org.example.buskmate.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.example.buskmate.dto.crud.c.BuskingCreateRequest;
import org.example.buskmate.dto.crud.d.BuskingDeleteRequest;
import org.example.buskmate.dto.crud.r.BuskingSelectAllResponse;
import org.example.buskmate.dto.crud.r.BuskingSelectOneRequest;
import org.example.buskmate.dto.crud.r.BuskingSelectOneResponse;
import org.example.buskmate.dto.crud.u.BuskingEditRequest;
import org.example.buskmate.dto.crud.u.BuskingEditResponse;
import org.example.buskmate.service.BuskingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/buskings")
@RequiredArgsConstructor
public class BuskingController {

    private final BuskingService buskingService;

    // 1. 생성
    @PostMapping("/create")
    @Operation(
            summary = "버스킹 생성",
            description = "새로운 버스킹 공연을 생성합니다."
    )
    @ApiResponse(
            responseCode = "201",
            description = "버스킹 공연이 성공적으로 생성되었습니다.",
            content = @Content (
            mediaType = "application/json",
            schema = @Schema(implementation = Void.class)
            )
    )
    public ResponseEntity<Void> buskingCreate(@RequestBody BuskingCreateRequest req) {
        buskingService.buskingCreate(req);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // 2. 전체 조회
    @Operation(
            summary = "전체 버스킹 조회",
            description = "등록 된 모든 버스킹 정보 반환"
    )
    @ApiResponse(
            responseCode = "200",
            description = "모든 버스킹 정보 반환 성공",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = BuskingSelectAllResponse.class)
            )
    )
    @GetMapping("/showall")
    public ResponseEntity<List<BuskingSelectAllResponse>> buskingSelectAll(){
        List<BuskingSelectAllResponse> responseList = buskingService.buskingSelectAll();
        return ResponseEntity.ok(responseList);
    }

    // 3. 단일 조회
    @GetMapping("/showone")
    @Operation(
            summary = "단일 버스킹 선택",
            description = "단일 버스킹 정보 반환"
    )
    @ApiResponse(
            responseCode = "200",
            description = "단일 정보 반환 성공",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = BuskingSelectOneResponse.class)
            )
    )
    public ResponseEntity<BuskingSelectOneResponse> buskingSelectOne(@ModelAttribute BuskingSelectOneRequest req){
        BuskingSelectOneResponse response = buskingService.buskingSelectOne(req);
        return ResponseEntity.ok(response);
    }

    // 4. 수정
    @PatchMapping("/edit")
    @Operation(
            summary = "버스킹 수정",
            description = "기존 버스킹 공연 정보를 수정합니다."
    )
    @ApiResponse(
            responseCode = "200",
            description = "버스킹 공연 정보가 성공적으로 수정되었습니다.",
            content = @Content (
                    mediaType = "application/json",
                    schema = @Schema(implementation = BuskingEditResponse.class)
            )
    )
    public ResponseEntity<BuskingEditResponse> buskingEdit(@RequestBody BuskingEditRequest req){
        BuskingEditResponse response = buskingService.buskingEdit(req);
        return ResponseEntity.ok(response);
    }

    // 5. 삭제
    @DeleteMapping("/delete")
    @Operation(
            summary = "버스킹 삭제",
            description = "기존 버스킹 공연 정보를 삭제합니다."
    )
    @ApiResponse(
            responseCode = "204",
            description = "버스킹 공연 정보가 성공적으로 삭제되었습니다.",
            content = @Content (
                    mediaType = "application/json",
                    schema = @Schema(implementation = Void.class)
            )
    )
    public ResponseEntity<Void> buskingDelete(@RequestBody BuskingDeleteRequest req){
        buskingService.buskingDelete(req);
        return ResponseEntity.noContent().build();
    }
}
