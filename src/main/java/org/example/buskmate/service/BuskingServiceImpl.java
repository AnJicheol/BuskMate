package org.example.buskmate.service;

import com.github.f4b6a3.ulid.UlidCreator; // ulid 라이브러리
import lombok.RequiredArgsConstructor;
import org.example.buskmate.domain.Busking;
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
import org.example.buskmate.repository.BuskingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BuskingServiceImpl implements BuskingService {

    BuskingRepository buskingRepo;

    // 1. 생성
    @Transactional
    public void buskingCreate(BuskingCreateRequest req) {
        String ulid = UlidCreator.getUlid().toString();
        Busking busking = new Busking(
                ulid,
                req.title(),
                req.place(),
                req.buskingStart(),
                req.buskingEnd()
        );
        buskingRepo.save(busking);
    }
    // 2. 조회
    public BuskingSelectAllResponse buskingSelectAll(BuskingSelectAllRequest req) {
//        // Implementation code here
//        BuskingSelectAllResponse response = buskingRepo.findAll();
        return null;
    }
    public BuskingSelectOneResponse buskingSelectOne(BuskingSelectOneRequest req) {
        // Implementation code here
        return null;
    }
    // 3. 수정
    public BuskingEditResponse buskingEdit(BuskingEditRequest req) {
        // Implementation code here
        return null;
    }
    // 4. 삭제
    public BuskingDeleteResponse buskingDelete(BuskingDeleteRequest req) {
        // Implementation code here
        return null;
    }
}
