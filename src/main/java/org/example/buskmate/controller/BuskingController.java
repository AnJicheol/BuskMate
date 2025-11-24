package org.example.buskmate.controller;


import lombok.RequiredArgsConstructor;
import org.example.buskmate.dto.crud.c.BuskingCreateRequest;
import org.example.buskmate.dto.crud.c.BuskingCreateResponse;
import org.example.buskmate.dto.crud.d.BuskingDeleteRequest;
import org.example.buskmate.dto.crud.d.BuskingDeleteResponse;
import org.example.buskmate.dto.crud.r.BuskingSelectAllRequest;
import org.example.buskmate.dto.crud.r.BuskingSelectAllResponse;
import org.example.buskmate.dto.crud.r.BuskingSelectOneRequest;
import org.example.buskmate.dto.crud.r.BuskingSelectOneResponse;
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
    public void buskingCreate(@RequestBody BuskingCreateRequest req) {
        buskingService.buskingCreate(req);
        return ResponseEntity.created.noContent().build();
    }

    // 2. 전체 조회
    @GetMapping("/showall")
    public ResponseEntity<BuskingSelectAllResponse> buskingSelectAll(@ModelAttribute BuskingSelectAllRequest req){
        BuskingSelectAllResponse response = buskingService.buskingSelectAll(req);
        return ResponseEntity.ok(response);
    }

    // 3. 단일 조회
    @GetMapping("/showone")
    public ResponseEntity<BuskingSelectOneResponse> buskingSelectOne(@ModelAttribute BuskingSelectOneRequest req){
        BuskingSelectOneResponse response = buskingService.buskingSelectOne(req);
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
