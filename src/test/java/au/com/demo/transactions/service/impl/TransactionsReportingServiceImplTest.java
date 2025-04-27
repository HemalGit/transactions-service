package au.com.demo.transactions.service.impl;

import au.com.demo.transactions.persistence.repository.TransactionRepo;
import au.com.demo.transactions.rest.model.TransactionsCostResponse;
import au.com.demo.transactions.rest.model.TransactionsVolumeResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TransactionsReportingServiceImplTest {

    @Mock private TransactionRepo transactionRepo;
    @InjectMocks private TransactionsReportingServiceImpl transactionsReportingService;

    @Test
    public void calculateTotalTransactionsCost_CustomerId_ProductCode() {
        when(transactionRepo.findTotalTransactionsCostByCustomerIdAndProductCode(anyString(), anyString()))
                .thenReturn(List.of(new BigDecimal(100), new BigDecimal(130), new BigDecimal(60)));

        TransactionsCostResponse transactionsCostResponse = transactionsReportingService
                .calculateTotalTransactionsCost("10001", "PRODUCT_005");

        assertEquals(new BigDecimal(290), transactionsCostResponse.getTotalCost());

        verify(transactionRepo, times(1))
                .findTotalTransactionsCostByCustomerIdAndProductCode(anyString(), anyString());
    }

    @Test
    public void calculateTotalTransactionsCost_CustomerId() {
        when(transactionRepo.findTotalTransactionsCostByCustomerId(anyString()))
                .thenReturn(List.of(new BigDecimal(50), new BigDecimal(120), new BigDecimal(60)));

        TransactionsCostResponse transactionsCostResponse = transactionsReportingService
                .calculateTotalTransactionsCost("10001", null);

        assertEquals(new BigDecimal(230), transactionsCostResponse.getTotalCost());

        verify(transactionRepo, times(1))
                .findTotalTransactionsCostByCustomerId(anyString());
    }

    @Test
    public void calculateTotalTransactionsCost_ProductCode() {
        when(transactionRepo.findTotalTransactionsCostByProductCode(anyString()))
                .thenReturn(List.of(new BigDecimal(45), new BigDecimal(55), new BigDecimal(20)));

        TransactionsCostResponse transactionsCostResponse = transactionsReportingService
                .calculateTotalTransactionsCost(null, "PRODUCT_005");

        assertEquals(new BigDecimal(120), transactionsCostResponse.getTotalCost());

        verify(transactionRepo, times(1))
                .findTotalTransactionsCostByProductCode(anyString());
    }

    @Test
    public void calculateTotalTransactionsCost_no_params_success() {
        when(transactionRepo.findTotalTransactionsCost())
                .thenReturn(List.of(new BigDecimal(20), new BigDecimal(150), new BigDecimal(10)));

        TransactionsCostResponse transactionsCostResponse = transactionsReportingService
                .calculateTotalTransactionsCost(null, null);

        assertEquals(new BigDecimal(180), transactionsCostResponse.getTotalCost());

        verify(transactionRepo, times(1))
                .findTotalTransactionsCost();
    }

    @Test
    void findTotalTransactionsByLocation_success() {
        when(transactionRepo.findTotalTransactionsByLocation(anyString()))
                .thenReturn(5);

        TransactionsVolumeResponse transactionsVolumeResponse = transactionsReportingService
                .findTotalTransactionsByLocation(anyString());

        assertEquals(5, transactionsVolumeResponse.getTotal());
        verify(transactionRepo, times(1))
                .findTotalTransactionsByLocation(anyString());
    }
}
