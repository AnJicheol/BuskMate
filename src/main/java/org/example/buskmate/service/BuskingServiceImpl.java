package org.example.buskmate.service;

import com.github.f4b6a3.ulid.UlidCreator; // ulid 라이브러리
import lombok.RequiredArgsConstructor;
import org.example.buskmate.domain.Busking;
import org.example.buskmate.dto.crud.c.BuskingCreateRequest;
import org.example.buskmate.dto.crud.d.BuskingDeleteRequest;
import org.example.buskmate.dto.crud.r.BuskingSelectAllResponse;
import org.example.buskmate.dto.crud.r.BuskingSelectOneRequest;
import org.example.buskmate.dto.crud.r.BuskingSelectOneResponse;
import org.example.buskmate.dto.crud.u.BuskingEditRequest;
import org.example.buskmate.dto.crud.u.BuskingEditResponse;
import org.example.buskmate.repository.BuskingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BuskingServiceImpl implements BuskingService {

    private final BuskingRepository buskingRepo;

    // 1. 생성
    @Transactional
    public void buskingCreate(BuskingCreateRequest req) {
        String ulid = UlidCreator.getUlid().toString();
        Busking busking = new Busking(
                ulid,
                req.title(),
                req.buskingStart(),
                req.buskingEnd()
        );
        buskingRepo.save(busking);
    }
    // 2. 조회
    // 2-1. 전체 조회
    @Transactional(readOnly = true)
    public List<BuskingSelectAllResponse> buskingSelectAll() {
        return buskingRepo.findAllBusking()
                .stream()
                .map(BuskingSelectAllResponse :: of)
                .toList();
    }
    // 2-2. 단일 조회
    @Transactional(readOnly = true)
    public BuskingSelectOneResponse buskingSelectOne(BuskingSelectOneRequest req) {
        return buskingRepo.findOneBusking(req.buskingId())
                .map(BuskingSelectOneResponse :: of)
                    .orElseThrow(() -> new IllegalArgumentException("해당 버스킹 없음"));
    }
    // 3. 수정
    @Transactional
    public BuskingEditResponse buskingEdit(BuskingEditRequest req) {
        Busking busking = buskingRepo.findOneBusking(req.buskingId())
                .orElseThrow(() -> new IllegalArgumentException("해당 버스킹 없음"));
        busking.setTitle(req.title());
        busking.setBuskingStart(req.buskingStart());
        busking.setBuskingEnd(req.buskingEnd());
        return BuskingEditResponse.of(busking);
    }
    // 4. 삭제
    @Transactional
    public void buskingDelete(BuskingDeleteRequest req) {
        Busking busking = buskingRepo.findOneBusking(req.buskingId())
                .orElseThrow(() -> new IllegalArgumentException("해당 버스킹 없음"));
        buskingRepo.delete(busking);
    }
}
