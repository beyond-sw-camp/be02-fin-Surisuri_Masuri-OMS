package com.example.Surisuri_Masuri.exception;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // @Valid를 통한 입력값 검증 시 발생하는 에러
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidException(MethodArgumentNotValidException e) {
        ErrorCode errorCode = ErrorCode.INVALID_INPUT_VALUE;
        BindingResult bindingResult = e.getBindingResult();

        StringBuilder builder = new StringBuilder();
        for (int i=0; i<bindingResult.getFieldErrors().size(); i++) {
            FieldError fieldError = bindingResult.getFieldErrors().get(i);
            builder.append("[");
            builder.append(fieldError.getField());
            builder.append("](은)는 ");
            builder.append(fieldError.getDefaultMessage());

            if(i<bindingResult.getFieldErrors().size()-1) {
                builder.append(" / ");
            }
        }

        ErrorResponse response = new ErrorResponse(errorCode.getCode(), builder.toString());
        return new ResponseEntity<>(response, errorCode.getStatus());
    }

    // 일관된 예외처리를 하도록 BusinessException을 상속
    @ExceptionHandler(BusinessException.class)
    protected ResponseEntity<ErrorResponse> handleBusinessException(BusinessException e) {
        ErrorCode errorCode = e.getErrorCode();
        ErrorResponse response = new ErrorResponse(errorCode.getCode(), e.getMessage());
        return new ResponseEntity<>(response, errorCode.getStatus());
    }

    // 401 에러 처리 - 권한
    @ExceptionHandler({AccessDeniedException.class})
    public ResponseEntity<ErrorResponse> handleInvalidPermissionException(AccessDeniedException e) {
        ErrorCode errorCode = ErrorCode.INVALID_PERMISION;
        ErrorResponse response = new ErrorResponse(errorCode.getCode(), e.getMessage());
        return new ResponseEntity<>(response, errorCode.getStatus());
    }

    // 404 에러 처리
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolationException(ConstraintViolationException e) {
        ErrorCode errorCode = ErrorCode.CONSTRAINT_VIOLATION;
        ErrorResponse response = new ErrorResponse(errorCode.getCode(), e.getMessage());
        return new ResponseEntity<>(response, errorCode.getStatus());
    }

    // 500 에러 처리
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResponse> handleException(Exception e) {
        ErrorCode errorCode = ErrorCode.INTERNAL_SERVER_ERROR;
        ErrorResponse response = new ErrorResponse(errorCode.getCode(), e.getMessage());
        return new ResponseEntity<>(response, errorCode.getStatus());
    }
}
