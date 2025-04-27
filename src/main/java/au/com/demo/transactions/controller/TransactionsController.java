package au.com.demo.transactions.controller;

import au.com.demo.transactions.rest.model.TransactionRequest;
import au.com.demo.transactions.rest.model.TransactionResponse;
import au.com.demo.transactions.service.TransactionsService;
import au.com.demo.transactions.util.BusinessValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
@Validated
public class TransactionsController {

    private final TransactionsService transactionsServiceImpl;
    private final BusinessValidator businessValidator;

    @PostMapping("transaction")
    public TransactionResponse createTransaction(@Valid @RequestBody TransactionRequest transactionRequest) {
        businessValidator.validateTransactionRequest(transactionRequest);
        return transactionsServiceImpl.createTransaction(transactionRequest);
    }
}