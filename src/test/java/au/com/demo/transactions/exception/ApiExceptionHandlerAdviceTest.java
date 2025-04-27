package au.com.demo.transactions.exception;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
public class ApiExceptionHandlerAdviceTest {

    @Test
    public void handleGeneralThrowable_Success() {
        ApiExceptionHandlerAdvice exceptionHandlerAdvice = new ApiExceptionHandlerAdvice();
        ResponseEntity<ApiError> responseEntity = exceptionHandlerAdvice.handleGeneralThrowable(new Exception("test message"));

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals("test message", responseEntity.getBody().getMessage());
    }

    @Test
    public void handleBusinessException_Success() {
        ApiExceptionHandlerAdvice exceptionHandlerAdvice = new ApiExceptionHandlerAdvice();
        ResponseEntity<ApiError> responseEntity = exceptionHandlerAdvice.handleBusinessException(
                BusinessException.badRequest("test bad request message"));

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals("test bad request message", responseEntity.getBody().getMessage());
    }
}
