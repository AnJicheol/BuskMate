package org.example.buskmate.band.service;

import org.example.buskmate.band.dto.BandCreateRequest;
import org.example.buskmate.band.dto.BandCreateResponse;

public interface BandService {

    BandCreateResponse create(BandCreateRequest request);
}
