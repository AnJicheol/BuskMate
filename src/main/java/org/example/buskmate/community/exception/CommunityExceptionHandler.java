package org.example.buskmate.community.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 커뮤니티 도메인 전역 예외 처리 핸들러
 * - 컨트롤러에서 발생한 예외를 공통 응답 포맷으로 변환한다.
 */
@RestControllerAdvice
public class CommunityExceptionHandler {

    /**
     * 에러 응답 바디 포맷
     */
    public record ErrorResponse(
            String code,
            String message
    ) {}

    /**
     * 잘못된 인자/존재하지 않는 리소스 등 IllegalArgumentException을 처리한다.
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException e) {
        ErrorResponse body = new ErrorResponse(
                "POST_INVALID_ARGUMENT",
                e.getMessage()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

    /**
     * 작성자 권한이 없는 접근(수정/삭제)을 처리한다.
     */
    @ExceptionHandler(UnauthorizedPostAccessException.class)
    public ResponseEntity<ErrorResponse> handleUnauthorizedPostAccessException(UnauthorizedPostAccessException e) {
        ErrorResponse body = new ErrorResponse(
                "POST_UNAUTHORIZED",
                e.getMessage()
        );

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(body);
    }
}
