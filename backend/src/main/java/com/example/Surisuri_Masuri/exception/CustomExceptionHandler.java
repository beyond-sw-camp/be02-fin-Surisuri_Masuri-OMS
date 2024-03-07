package backend.src.main.java.com.example.Surisuri_Masuri.exception;

import common.BaseResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(CustomException.class)
    protected ResponseEntity<Object> handleCustomException(CustomException e){
        return ResponseEntity.status(e.getErrorCode().getStatus())
                .body(BaseResponse.failResponse(e.getErrorCode().getCode(), e.getErrorCode().name()));
    }
}
