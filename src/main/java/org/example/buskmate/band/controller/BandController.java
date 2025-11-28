package org.example.buskmate.band.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.buskmate.band.dto.band.*;
import org.example.buskmate.band.service.BandService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/bands")
public class BandController {

    private final BandService bandService;

    @PostMapping
    public ResponseEntity<BandCreateResponse> create(
            @Valid @RequestBody BandCreateRequest request
    ) {
        return ResponseEntity.ok(bandService.create(request));
    }


    @GetMapping
    public ResponseEntity<List<BandListItemResponse>> getAll() {
        return ResponseEntity.ok(bandService.getAllBands());
    }

    @GetMapping("/{bandId}")
    public ResponseEntity<BandDetailResponse> getById(@PathVariable String bandId) {
        return ResponseEntity.ok(bandService.getByBandId(bandId));
    }

    @PatchMapping("/{bandId}")
    public ResponseEntity<BandDetailResponse> updateBand(
            @PathVariable String bandId,
            @RequestBody UpdateBandRequest request
    ) {
        BandDetailResponse response = bandService.updateBand(bandId, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{bandId}")
    public ResponseEntity<Void> deleteBand(@PathVariable String bandId) {
        bandService.deactivate(bandId);
        return ResponseEntity.noContent().build();
    }
}