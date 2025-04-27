package au.com.demo.transactions.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ApiExceptionHandlerAdvice {

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<ApiError> handleGeneralThrowable(final Throwable throwable) {

        log.error("API error encountered. Please refer to logs for more details. Error: {}", throwable.getMessage());
        ApiError errorResponse = new ApiError();
        errorResponse.setMessage(throwable.getMessage());
        errorResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiError> handleBusinessException(final BusinessException businessException) {

        log.error("BusinessException encountered. Please refer to logs for more details. Error: {}", businessException.getMessage());
        ApiError errorResponse = new ApiError();
        errorResponse.setMessage(businessException.getMessage());
        errorResponse.setStatus(businessException.getHttpStatus().value());
        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(errorResponse.getStatus()));
    }
}
