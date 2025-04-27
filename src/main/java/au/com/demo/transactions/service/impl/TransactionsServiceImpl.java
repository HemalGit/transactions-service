package au.com.demo.transactions.service.impl;

import au.com.demo.transactions.persistence.entity.Transaction;
import au.com.demo.transactions.persistence.repository.TransactionRepo;
import au.com.demo.transactions.rest.model.TransactionRequest;
import au.com.demo.transactions.rest.model.TransactionResponse;
import au.com.demo.transactions.service.TransactionsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static au.com.demo.transactions.constants.Constants.DATE_TIME_INPUT_FORMAT;
import static au.com.demo.transactions.util.DateTimeConverter.convertToOffsetDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionsServiceImpl implements TransactionsService {

    private final TransactionRepo transactionRepo;

    @Override
    public TransactionResponse createTransaction(TransactionRequest transactionRequest) {

        Transaction transactionEntity = new Transaction();
        transactionEntity.setTransactionTime(
                convertToOffsetDateTime(transactionRequest.getTransactionTime(), DATE_TIME_INPUT_FORMAT));
        transactionEntity.setCustomerId(transactionRequest.getCustomerId());
        transactionEntity.setProductCode(transactionRequest.getProductCode());
        transactionEntity.setQuantity(transactionRequest.getQuantity());

        Transaction savedTransactionEntity = transactionRepo.save(transactionEntity);
        return new TransactionResponse().transactionId(savedTransactionEntity.getId().toString());
    }
}
