package org.example.buskmate.service;

import com.github.f4b6a3.ulid.UlidCreator; // ulid 라이브러리
import lombok.RequiredArgsConstructor;
import org.example.buskmate.domain.Busking;
import org.example.buskmate.dto.crud.c.BuskingCreateRequest;
import org.example.buskmate.dto.crud.c.BuskingCreateResponse;
import org.example.buskmate.dto.crud.d.BuskingDeleteRequest;
import org.example.buskmate.dto.crud.d.BuskingDeleteResponse;
import org.example.buskmate.dto.crud.r.BuskingSellectAllResponse;
import org.example.buskmate.dto.crud.r.BuskingSellectOneRequest;
import org.example.buskmate.dto.crud.u.BuskingEditRequest;
import org.example.buskmate.dto.crud.u.BuskingEditResponse;
import org.example.buskmate.repository.BuskingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class BuskingServiceImpl implements BuskingService {

    BuskingRepository buskingRepo;

    // 1. 생성
    @Transactional
    public BuskingCreateResponse buskingCreate(BuskingCreateRequest req) {
        // Implementation code here
        String ulid = UlidCreator.getUlid().toString();
        Busking busking = new Busking(
                ulid,
                req.title(),
                req.place(),
                req.buskingStart(),
                req.buskingEnd()
        );
        buskingRepo.save(busking);

        return null;
    }
    // 2. 조회
    public BuskingSellectAllResponse buskingShowAll(BuskingShowAllRequest req) {
        // Implementation code here
        return null;
    }
    public BuskingShowOneResponse buskingShowOne(BuskingSellectOneRequest req) {
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
