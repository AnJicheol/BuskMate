package org.example.buskmate.service;

import org.example.buskmate.dto.crud.c.BuskingCreateRequest;
import org.example.buskmate.dto.crud.c.BuskingCreateResponse;
import org.example.buskmate.dto.crud.d.BuskingDeleteRequest;
import org.example.buskmate.dto.crud.d.BuskingDeleteResponse;
import org.example.buskmate.dto.crud.r.BuskingShowAllRequest;
import org.example.buskmate.dto.crud.r.BuskingSellectAllResponse;
import org.example.buskmate.dto.crud.r.BuskingSellectOneRequest;
import org.example.buskmate.dto.crud.r.BuskingShowOneResponse;
import org.example.buskmate.dto.crud.u.BuskingEditRequest;
import org.example.buskmate.dto.crud.u.BuskingEditResponse;

public interface BuskingService {
    // 1. 생성
    public BuskingCreateResponse buskingCreate(BuskingCreateRequest req);
    // 2. 조회
    public BuskingSellectAllResponse buskingShowAll(BuskingShowAllRequest req);
    public BuskingShowOneResponse buskingShowOne(BuskingSellectOneRequest req);
    // 3. 수정
    public BuskingEditResponse buskingEdit(BuskingEditRequest req);
    // 4. 삭제
    public BuskingDeleteResponse buskingDelete(BuskingDeleteRequest req);
}
