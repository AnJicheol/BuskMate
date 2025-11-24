package org.example.buskmate.controller;


import lombok.RequiredArgsConstructor;
import org.example.buskmate.dto.crud.c.BuskingCreateRequest;
import org.example.buskmate.dto.crud.c.BuskingCreateResponse;
import org.example.buskmate.dto.crud.d.BuskingDeleteRequest;
import org.example.buskmate.dto.crud.d.BuskingDeleteResponse;
import org.example.buskmate.dto.crud.r.BuskingSellectAllRequest;
import org.example.buskmate.dto.crud.r.BuskingSellectAllResponse;
import org.example.buskmate.dto.crud.r.BuskingSellectOneRequest;
import org.example.buskmate.dto.crud.r.BuskingSellectOneResponse;
import org.example.buskmate.dto.crud.u.BuskingEditRequest;
import org.example.buskmate.dto.crud.u.BuskingEditResponse;
import org.example.buskmate.service.BuskingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/buskings")
@RequiredArgsConstructor
public class BuskingController {

    private final BuskingService buskingService;

    // 1. 생성
    @PostMapping("/create")
    public ResponseEntity<BuskingCreateResponse> buskingCreate(@RequestBody BuskingCreateRequest req) {
        BuskingCreateResponse response = buskingService.buskingCreate(req);
        return ResponseEntity.ok(response);
    }

    // 2. 전체 조회
    @GetMapping("/showall")
    public ResponseEntity<BuskingSellectAllResponse> buskingShowAll(@ModelAttribute BuskingSellectAllRequest req){
        BuskingSellectAllResponse response = buskingService.buskingShowAll(req);
        return ResponseEntity.ok(response);
    }

    // 3. 단일 조회
    @GetMapping("/showone")
    public ResponseEntity<BuskingSellectOneResponse> buskingShowOne(@ModelAttribute BuskingSellectOneRequest req){
        BuskingSellectOneResponse response = buskingService.buskingShowOne(req);
        return ResponseEntity.ok(response);
    }

    // 4. 수정
    @PatchMapping("/edit")
    public ResponseEntity<BuskingEditResponse> buskingEdit(@RequestBody BuskingEditRequest req){
        BuskingEditResponse response = buskingService.buskingEdit(req);
        return ResponseEntity.ok(response);
    }

    // 5. 삭제
    @DeleteMapping("/delete")
    public ResponseEntity<BuskingDeleteResponse> buskingDelete(@RequestBody BuskingDeleteRequest req){
        BuskingDeleteResponse response = buskingService.buskingDelete(req);
        return ResponseEntity.noContent().build();
    }
}
