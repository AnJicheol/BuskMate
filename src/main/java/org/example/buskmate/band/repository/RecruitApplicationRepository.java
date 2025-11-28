package org.example.buskmate.band.repository;

import org.example.buskmate.band.domain.RecruitApplication;
import org.example.buskmate.band.domain.RecruitApplicationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecruitApplicationRepository extends JpaRepository<RecruitApplication, Long> {
    RecruitApplication findByApplicationId(String applicationId);

    List<RecruitApplication> findAllByRecruitPost_PostId(String postId);
    List<RecruitApplication> findAllByPost_PostIdAndStatus(String postId, RecruitApplicationStatus status
    );

}
