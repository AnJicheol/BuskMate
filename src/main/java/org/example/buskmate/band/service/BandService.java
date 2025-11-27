package org.example.buskmate.band.service;

import org.example.buskmate.band.dto.*;

import java.util.List;

public interface BandService {

    BandCreateResponse create(BandCreateRequest request);

    BandDetailResponse getByBandId(String bandId);
    List<BandListItemResponse> getAllBands();

    BandDetailResponse updateBand(String bandId, UpdateBandRequest req);
}
