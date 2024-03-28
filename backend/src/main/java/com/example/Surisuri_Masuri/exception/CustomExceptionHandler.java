//package com.example.Surisuri_Masuri.exception;
//
//import com.example.Surisuri_Masuri.common.BaseResponse;
//import org.hibernate.exception.ConstraintViolationException;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//
//@RestControllerAdvice
//public class CustomExceptionHandler {
//
//    @ExceptionHandler(ConstraintViolationException.class)
//    public ResponseEntity<ErrorResponse> handleConstraintViolationException(ConstraintViolationException e) {
//        ErrorCode errorCode = ErrorCode.CONSTRAINT_VIOLATION;
//        ErrorResponse response = new ErrorResponse(errorCode.getCode(), e.getMessage());
//        return ResponseEntity.ok().body(response);
//    }
//
//    @ExceptionHandler(Exception.class)
//    protected ResponseEntity<ErrorResponse> handleException(Exception e) {
//        ErrorCode errorCode = ErrorCode.INTERNAL_SERVER_ERROR;
//        ErrorResponse response = new ErrorResponse(errorCode.getCode(), e.getMessage());
//        return ResponseEntity.ok().body(response);
//    }
//}
