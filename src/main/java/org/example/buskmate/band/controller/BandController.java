package org.example.buskmate.band.controller;

import lombok.RequiredArgsConstructor;
import org.example.buskmate.band.dto.BandCreateRequest;
import org.example.buskmate.band.dto.BandCreateResponse;
import org.example.buskmate.band.service.BandService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/bands")
public class BandController {

    private final BandService bandService;

    @PostMapping
    public ResponseEntity<BandCreateResponse> create(
            @RequestBody BandCreateRequest request
    ) {
        BandCreateResponse response =
                bandService.create(request);

        return ResponseEntity.ok(response);
    }
}