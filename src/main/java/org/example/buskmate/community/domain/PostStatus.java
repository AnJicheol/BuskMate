package org.example.buskmate.community.domain;

/**
 * 게시글/댓글의 활성 상태
 * - ACTIVE: 정상 노출 상태
 * - DELETED: 소프트 삭제 상태
 */
public enum PostStatus {
    ACTIVE,
    DELETED
}
