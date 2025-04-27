package au.com.demo.transactions.service;

import au.com.demo.transactions.rest.model.TransactionsCostResponse;
import au.com.demo.transactions.rest.model.TransactionsVolumeResponse;

public interface TransactionsReportingService {

    TransactionsCostResponse calculateTotalTransactionsCost(final String customerId, final String productCode);

    TransactionsVolumeResponse findTotalTransactionsByLocation(final String location);
}
