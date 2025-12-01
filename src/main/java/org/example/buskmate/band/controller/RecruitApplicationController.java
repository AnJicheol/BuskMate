package org.example.buskmate.band.controller;


import lombok.RequiredArgsConstructor;
import org.example.buskmate.band.dto.recruitapplication.RecruitApplyResponseDto;
import org.example.buskmate.band.service.RecruitApplicationService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/recruit")
public class RecruitApplicationController {
    private final RecruitApplicationService recruitApplicationService;

    @PostMapping("/{postId}/apply")
    public ResponseEntity<RecruitApplyResponseDto> apply(@PathVariable String postId, @AuthenticationPrincipal CustomUser user){
        RecruitApplyResponseDto response = recruitApplicationService.apply(postId, user.getUserId());

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/apply/{applicationId}")
    public ResponseEntity<Void> delete(@PathVariable String applicationId, @AuthenticationPrincipal CustomUser user){
        recruitApplicationService.delete(applicationId, user.getUserId());
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{applicationId}/accept")
    public ResponseEntity<Void> accept(@PathVariable String applicationId, @AuthenticationPrincipal CustomUser user){
        recruitApplicationService.accept(applicationId, user.getUserId());
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{applicationId}/reject")
    public ResponseEntity<Void> reject(@PathVariable String applicationId, @AuthenticationPrincipal CustomUser user){
        recruitApplicationService.reject(applicationId, user.getUserId());
        return ResponseEntity.noContent().build();
    }
}
