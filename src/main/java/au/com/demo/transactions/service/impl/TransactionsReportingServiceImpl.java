package au.com.demo.transactions.service.impl;

import au.com.demo.transactions.persistence.repository.TransactionRepo;
import au.com.demo.transactions.rest.model.TransactionsCostResponse;
import au.com.demo.transactions.rest.model.TransactionsVolumeResponse;
import au.com.demo.transactions.service.TransactionsReportingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionsReportingServiceImpl implements TransactionsReportingService {

    private final TransactionRepo transactionRepo;

    @Override
    public TransactionsCostResponse calculateTotalTransactionsCost(String customerId, String productCode) {
        List<BigDecimal> totalTxCost;

        if (StringUtils.isNotBlank(customerId) && StringUtils.isNotBlank(productCode)) {
            totalTxCost = transactionRepo.findTotalTransactionsCostByCustomerIdAndProductCode(customerId, productCode);

        } else if (StringUtils.isNotBlank(customerId) && StringUtils.isBlank(productCode)) {
            totalTxCost = transactionRepo.findTotalTransactionsCostByCustomerId(customerId);

        } else if (StringUtils.isBlank(customerId) && StringUtils.isNotBlank(productCode)) {
            totalTxCost = transactionRepo.findTotalTransactionsCostByProductCode(productCode);

        } else {
            totalTxCost = transactionRepo.findTotalTransactionsCost();
        }

        TransactionsCostResponse costResponse = new TransactionsCostResponse();
        costResponse.setTotalCost(totalTxCost.stream().reduce(BigDecimal.ZERO, BigDecimal::add));
        return costResponse;
    }

    @Override
    public TransactionsVolumeResponse findTotalTransactionsByLocation(String location) {

        TransactionsVolumeResponse volumeResponse = new TransactionsVolumeResponse();
        volumeResponse.setTotal(transactionRepo.findTotalTransactionsByLocation(location));
        return volumeResponse;
    }
}
