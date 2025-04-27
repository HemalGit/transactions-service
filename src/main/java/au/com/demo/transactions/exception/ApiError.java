package au.com.demo.transactions.exception;

import lombok.Data;

@Data
public class ApiError {

    private int status;
    private String message;
}
