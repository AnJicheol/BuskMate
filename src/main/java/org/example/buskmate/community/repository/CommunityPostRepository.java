package org.example.buskmate.community.repository;

import org.example.buskmate.community.domain.CommunityPost;
import org.example.buskmate.community.domain.PostStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface CommunityPostRepository extends JpaRepository<CommunityPost,Long> {

    @Query("""
            select cp
            from CommunityPost cp
            where cp.isActive = :isACTIVE
            """)
    Page<CommunityPost> findByIsActive(PostStatus isActive, Pageable page);
}
