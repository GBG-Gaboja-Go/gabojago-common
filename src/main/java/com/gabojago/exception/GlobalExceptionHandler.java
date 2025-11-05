package com.gabojago.exception;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class GlobalExceptionHandler {

    /**
     * 비즈니스 예외
     * @param appException
     * @return
     */
    @ExceptionHandler(AppException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(AppException appException) {

        return ResponseEntity.status(appException.getErrorCode().getStatus())
            .body(ErrorResponse.from(appException));
    }

    /**
     * @Valid 유효성 검증 실패
     * @param appException
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException appException) {
        List<ErrorResponse.ErrorField>  errors = appException.getBindingResult()
            .getFieldErrors()
            .stream()
            .map(f -> new ErrorResponse.ErrorField(f.getField(), f.getDefaultMessage()))
            .toList();

        ErrorCode validationCode = new ErrorCode() {
            @Override
            public String getCode() {
                return "INVALID_INPUT";
            }

            @Override
            public String getMessage() {
                return "입력값이 유효하지 않습니다.";
            }

            @Override
            public HttpStatus getStatus() {
                return HttpStatus.BAD_REQUEST;
            }
        };

        return ResponseEntity
            .status(validationCode.getStatus())
            .body(ErrorResponse.of(validationCode, errors));
    }

    /**
     * 그 외 모든 예외
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneralException(Exception e) {
        ErrorCode internal = new ErrorCode() {
            @Override
            public String getCode() {
                return "INTERNAL_SERVER_ERROR";
            }

            @Override
            public String getMessage() {
                return "서버 내부 오류가 발생했습니다.";
            }

            @Override
            public HttpStatus getStatus() {
                return HttpStatus.INTERNAL_SERVER_ERROR;
            }
        };

        return ResponseEntity
            .status(internal.getStatus())
            .body(ErrorResponse.from(internal));
    }

}