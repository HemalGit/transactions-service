package au.com.demo.transactions.exception;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BusinessException extends RuntimeException {

    private final String message;
    private final HttpStatus httpStatus;

    private BusinessException(final HttpStatus httpStatus, final String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public static BusinessException badRequest(@NotBlank final String message) {
        return new BusinessException(HttpStatus.BAD_REQUEST, message);
    }
}
