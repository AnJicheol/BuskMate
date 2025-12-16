package org.example.buskmate.community.exception;

/**
 * 게시글 수정/삭제 등 작성자 권한이 필요한 작업에서 권한이 없을 때 발생하는 예외
 */
public class UnauthorizedPostAccessException extends RuntimeException {
    public UnauthorizedPostAccessException(String message) {
        super(message);
    }
}
