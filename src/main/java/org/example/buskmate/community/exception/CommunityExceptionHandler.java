package org.example.buskmate.community.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CommunityExceptionHandler {

    public record ErrorResponse(
            String code,
            String message
    ) {}

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException e) {
        ErrorResponse body = new ErrorResponse(
                "POST_INVALID_ARGUMENT",
                e.getMessage()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

    @ExceptionHandler(UnauthorizedPostAccessException.class)
    public ResponseEntity<ErrorResponse> handleUnauthorizedPostAccessException(UnauthorizedPostAccessException e) {
        ErrorResponse body = new ErrorResponse(
                "POST_UNAUTHORIZED",
                e.getMessage()
        );

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(body);
    }
}
