package org.example.buskmate.service;

import org.example.buskmate.dto.crud.c.BuskingCreateRequest;
import org.example.buskmate.dto.crud.d.BuskingDeleteRequest;
import org.example.buskmate.dto.crud.r.BuskingSelectAllResponse;
import org.example.buskmate.dto.crud.r.BuskingSelectOneRequest;
import org.example.buskmate.dto.crud.r.BuskingSelectOneResponse;
import org.example.buskmate.dto.crud.u.BuskingEditRequest;
import org.example.buskmate.dto.crud.u.BuskingEditResponse;

import java.util.List;

public interface BuskingService {
    // 1. 생성
    void buskingCreate(BuskingCreateRequest req);
    // 2. 조회
    List<BuskingSelectAllResponse> buskingSelectAll();                      // 전체 조회
    BuskingSelectOneResponse buskingSelectOne(BuskingSelectOneRequest req); // 단일 조회
    // 3. 수정
    BuskingEditResponse buskingEdit(BuskingEditRequest req);
    // 4. 삭제
    void buskingDelete(BuskingDeleteRequest req);
}
