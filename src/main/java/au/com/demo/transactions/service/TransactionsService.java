package au.com.demo.transactions.service;

import au.com.demo.transactions.rest.model.TransactionRequest;
import au.com.demo.transactions.rest.model.TransactionResponse;

public interface TransactionsService {

    TransactionResponse createTransaction(TransactionRequest transactionRequest);
}
