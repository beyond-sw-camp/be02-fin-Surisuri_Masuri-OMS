package backend.src.main.java.com.example.Surisuri_Masuri.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CustomException extends RuntimeException {
    private ErrorCode errorCode;
    private String message;
    private Integer code;

    public CustomException(ErrorCode errorCode) {
        this.errorCode = errorCode;
        this.message = null;
        this.code = null;
    }

    public CustomException(ErrorCode errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }


    public String getMessage() {
        if (message == null) {
            return errorCode.getMessage();
        }

        return String.format("%s. %s", errorCode.getMessage(), message);
    }
}
