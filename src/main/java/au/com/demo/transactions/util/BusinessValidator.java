package au.com.demo.transactions.util;

import au.com.demo.transactions.exception.BusinessException;
import au.com.demo.transactions.persistence.entity.Product;
import au.com.demo.transactions.persistence.repository.ProductRepo;
import au.com.demo.transactions.rest.model.TransactionRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import static au.com.demo.transactions.constants.Constants.DATE_TIME_INPUT_FORMAT;
import static au.com.demo.transactions.constants.Constants.LOCAL_TIME_ZONE;

@Component
@RequiredArgsConstructor
public class BusinessValidator {

    private final ProductRepo productRepo;

    public void validateTransactionRequest(TransactionRequest transactionRequest) {

        if (LocalDateTime.parse(transactionRequest.getTransactionTime(), DateTimeFormatter.ofPattern(DATE_TIME_INPUT_FORMAT))
                .isBefore(LocalDateTime.now(ZoneId.of(LOCAL_TIME_ZONE)))) {
            throw BusinessException.badRequest("Transaction date time cannot be a past date time");
        }

        if (transactionRequest.getQuantity() < 1) {
            throw BusinessException.badRequest("Product quantity must be greater than 0");
        }

        Product productEntity = productRepo.findByProductCode(transactionRequest.getProductCode());

        if (productEntity == null) {
            throw BusinessException.badRequest("Invalid product code in transaction request");

        } else if (!productEntity.getStatus().equalsIgnoreCase("Active")) {
            throw BusinessException.badRequest("Inactive product code in transaction request");

        } else if (productEntity.getCost().multiply(BigDecimal.valueOf(transactionRequest.getQuantity()))
                .compareTo(BigDecimal.valueOf(5000)) > 0) {

            throw BusinessException.badRequest("Total cost of transaction must not exceed 5000");
        }
    }
}
