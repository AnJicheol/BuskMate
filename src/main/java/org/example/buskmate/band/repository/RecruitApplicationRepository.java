package org.example.buskmate.band.repository;

import org.example.buskmate.band.domain.RecruitApplication;
import org.example.buskmate.band.domain.RecruitApplicationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RecruitApplicationRepository extends JpaRepository<RecruitApplication, Long> {
    Optional<RecruitApplication> findByApplicationId(String applicationId);

    List<RecruitApplication> findAllByRecruitPost_PostId(String postId);
    List<RecruitApplication> findAllByRecruitPost_PostIdAndStatus(String postId, RecruitApplicationStatus status);

    boolean existsByRecruitPost_PostIdAndApplicantId(String postId, String applicantId);
}
