package org.example.buskmate.community.repository;

import org.example.buskmate.community.domain.CommunityPost;
import org.example.buskmate.community.domain.PostStatus;
import org.example.buskmate.community.dto.post.crud.response.CommunityPostReadAllPostResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CommunityPostRepository extends JpaRepository<CommunityPost,Long> {
    @Query(
        value = """
            select new org.example.buskmate.community.dto.post.crud.response.CommunityPostReadAllPostResponse(
                p.id,
                p.authorId,
                p.title,
                cast(coalesce(count(distinct l.viewerId), 0L) as Long) as viewCount,
                p.createdAt,
                cast(coalesce(count(distinct c.id), 0L) as Long) as commentCount
                )
            from CommunityPost p
            left join CommunityPostLog l on l.communityPost = p
            left join CommunityComment c on c.communityPost = p and c.isActive = :status
            where p.isActive = :status
            group by p.id, p.authorId, p.title, p.createdAt
            order by p.createdAt DESC
        """,
        countQuery = """
            select count(p)
            from CommunityPost p
            where p.isActive = :status
        """
    )
    Page<CommunityPostReadAllPostResponse> findAllPostSummary(
            @Param("status") PostStatus status,
            Pageable pageable
    );
}