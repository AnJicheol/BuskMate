package org.example.buskmate.band.service;

import org.example.buskmate.band.dto.BandCreateRequest;
import org.example.buskmate.band.dto.BandCreateResponse;
import org.example.buskmate.band.dto.BandDetailResponse;
import org.example.buskmate.band.dto.BandListItemResponse;

import java.util.List;

public interface BandService {

    BandCreateResponse create(BandCreateRequest request);

    BandDetailResponse getByBandId(String bandId);
    List<BandListItemResponse> getAllBands();
}
