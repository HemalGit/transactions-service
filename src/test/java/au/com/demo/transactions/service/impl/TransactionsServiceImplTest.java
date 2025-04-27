package au.com.demo.transactions.service.impl;

import au.com.demo.transactions.persistence.entity.Transaction;
import au.com.demo.transactions.persistence.repository.TransactionRepo;
import au.com.demo.transactions.rest.model.TransactionRequest;
import au.com.demo.transactions.rest.model.TransactionResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TransactionsServiceImplTest {

    @Mock private TransactionRepo transactionRepo;
    @InjectMocks private TransactionsServiceImpl transactionsService;

    @Test
    public void createTransaction_success() {
        Transaction savedTransaction = new Transaction();
        UUID transactionId = UUID.randomUUID();
        savedTransaction.setId(transactionId);
        when(transactionRepo.save(any(Transaction.class))).thenReturn(savedTransaction);

        TransactionRequest transactionRequest = new TransactionRequest();
        transactionRequest.setTransactionTime("2025-04-20 10:45");
        TransactionResponse transactionResponse = transactionsService.createTransaction(transactionRequest);

        assertEquals(transactionResponse.getTransactionId(), savedTransaction.getId().toString());
    }
}
