package org.example.buskmate.band.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

/**
 * 모집 글에 대한 사용자 지원 정보를 나타내는 도메인 엔티티입니다.
 *
 * <p>
 * {@code RecruitApplication}은 사용자가 특정 모집 글({@link RecruitPost})에
 * 지원한 이력을 관리하며, 지원 상태(WAITING / ACCEPTED / REJECTED / DELETED)를
 * 통해 모집 진행 상황을 표현합니다.
 * </p>
 *
 * <h3>주요 특징</h3>
 * <ul>
 *   <li>모집 글 단위로 사용자의 지원 이력을 관리</li>
 *   <li>지원 수락 / 거절 / 취소(삭제) 상태 전이 제공</li>
 *   <li>실제 물리 삭제가 아닌 상태 기반 관리</li>
 * </ul>
 *
 * <p>
 * 해당 엔티티는 모집 글({@link RecruitPost})과 다대일(N:1) 관계를 가지며,
 * 지원 시점은 {@link CreationTimestamp}를 통해 자동으로 기록됩니다.
 * </p>
 */
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
    /**
     * 모집 지원을 수락 상태로 변경합니다.
     *
     * <p>
     * 밴드 리더에 의해 호출되며,
     * 지원 상태는 {@link RecruitApplicationStatus#ACCEPTED}로 변경됩니다.
     * </p>
     */
    public void accept(){this.status=RecruitApplicationStatus.ACCEPTED;}
    /**
     * 모집 지원을 거절 상태로 변경합니다.
     *
     * <p>
     * 밴드 리더에 의해 호출되며,
     * 지원 상태는 {@link RecruitApplicationStatus#REJECTED}로 변경됩니다.
     * </p>
     */
    public void reject(){this.status=RecruitApplicationStatus.REJECTED;}
    /**
     * 모집 지원을 삭제(취소) 상태로 변경합니다.
     *
     * <p>
     * 실제 데이터 삭제가 아닌,
     * 상태를 {@link RecruitApplicationStatus#DELETED}로 변경하여
     * 논리적으로 지원을 취소합니다.
     * </p>
     */
    public void delete(){this.status=RecruitApplicationStatus.DELETED;}


}
