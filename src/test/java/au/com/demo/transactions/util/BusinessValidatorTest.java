package au.com.demo.transactions.util;

import au.com.demo.transactions.exception.BusinessException;
import au.com.demo.transactions.persistence.entity.Product;
import au.com.demo.transactions.persistence.repository.ProductRepo;
import au.com.demo.transactions.rest.model.TransactionRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BusinessValidatorTest {

    @Mock
    private ProductRepo productRepo;

    @InjectMocks
    private BusinessValidator businessValidator;

    @Test
    public void validateTransactionRequest_PastDate() {
        TransactionRequest transactionRequest = new TransactionRequest();
        transactionRequest.setTransactionTime("2020-01-15 08:30");

        BusinessException businessException =
                assertThrows(BusinessException.class,
                        () -> businessValidator.validateTransactionRequest(transactionRequest));

        assertEquals("Transaction date time cannot be a past date time", businessException.getMessage());
        verify(productRepo, never()).findByProductCode(anyString());
    }

    @Test
    public void validateTransactionRequest_InvalidQuantity() {
        TransactionRequest transactionRequest = new TransactionRequest();
        transactionRequest.setTransactionTime("2025-12-15 08:30");
        transactionRequest.setQuantity(0);

        BusinessException businessException =
                assertThrows(BusinessException.class,
                        () -> businessValidator.validateTransactionRequest(transactionRequest));

        assertEquals("Product quantity must be greater than 0", businessException.getMessage());
        verify(productRepo, never()).findByProductCode(anyString());
    }

    @Test
    public void validateTransactionRequest_NoProductAvailable() {
        TransactionRequest transactionRequest = new TransactionRequest();
        transactionRequest.setProductCode("PRODUCT_001");
        transactionRequest.setTransactionTime("2025-12-15 08:30");
        transactionRequest.setQuantity(1);

        when(productRepo.findByProductCode(anyString())).thenReturn(null);
        BusinessException businessException =
                assertThrows(BusinessException.class,
                        () -> businessValidator.validateTransactionRequest(transactionRequest));

        assertEquals("Invalid product code in transaction request", businessException.getMessage());
        verify(productRepo, times(1)).findByProductCode(anyString());
    }

    @Test
    public void validateTransactionRequest_InactiveProduct() {
        TransactionRequest transactionRequest = new TransactionRequest();
        transactionRequest.setProductCode("PRODUCT_001");
        transactionRequest.setTransactionTime("2025-12-15 08:30");
        transactionRequest.setQuantity(1);

        Product product = new Product();
        product.setStatus("Inactive");
        when(productRepo.findByProductCode(anyString())).thenReturn(product);

        BusinessException businessException =
                assertThrows(BusinessException.class,
                        () -> businessValidator.validateTransactionRequest(transactionRequest));

        assertEquals("Inactive product code in transaction request", businessException.getMessage());
        verify(productRepo, times(1)).findByProductCode(anyString());
    }

    @Test
    public void validateTransactionRequest_ExceedMaximumCost() {
        TransactionRequest transactionRequest = new TransactionRequest();
        transactionRequest.setProductCode("PRODUCT_001");
        transactionRequest.setTransactionTime("2025-12-15 08:30");
        transactionRequest.setQuantity(100);

        Product product = new Product();
        product.setStatus("Active");
        product.setCost(new BigDecimal(200));
        when(productRepo.findByProductCode(anyString())).thenReturn(product);

        BusinessException businessException =
                assertThrows(BusinessException.class,
                        () -> businessValidator.validateTransactionRequest(transactionRequest));

        assertEquals("Total cost of transaction must not exceed 5000", businessException.getMessage());
        verify(productRepo, times(1)).findByProductCode(anyString());
    }
}
