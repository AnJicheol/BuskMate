package org.example.buskmate.recruit.application.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.buskmate.recruit.post.domain.RecruitPost;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
@Table(name="recruit_application")
public class RecruitApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="application_id", nullable = false, unique = true)
    private String applicationId;

    @Column(name="user_id", nullable = false)
    private String applicantId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="post_id", nullable = false)
    private RecruitPost recruitPost;

    @Enumerated(EnumType.STRING)
    private RecruitApplicationStatus status;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime appliedAt;

    @Builder
    private RecruitApplication(String applicationId, String applicantId, RecruitPost recruitPost) {
        this.applicationId = applicationId;
        this.applicantId = applicantId;
        this.recruitPost = recruitPost;
        this.status = RecruitApplicationStatus.WAITING;
    }

    public void accept(){this.status=RecruitApplicationStatus.ACCEPTED;}
    public void reject(){this.status=RecruitApplicationStatus.REJECTED;}
    public void delete(){this.status=RecruitApplicationStatus.DELETED;}


}
