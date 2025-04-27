package au.com.demo.transactions.controller;

import au.com.demo.transactions.rest.model.TransactionsCostResponse;
import au.com.demo.transactions.rest.model.TransactionsVolumeResponse;
import au.com.demo.transactions.service.TransactionsReportingService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reporting/transactions/")
@RequiredArgsConstructor
@Validated
public class TransactionsReportingController {

    private final TransactionsReportingService transactionsReportingServiceImpl;

    @GetMapping("cost")
    public TransactionsCostResponse reportingTotalCost(
            @Valid @RequestParam(value = "customerId", required = false) String customerId,
            @Valid @RequestParam(value = "productCode", required = false) String productCode) {

        return transactionsReportingServiceImpl.calculateTotalTransactionsCost(customerId, productCode);
    }

    @GetMapping("volume")
    public TransactionsVolumeResponse reportingTotalVolume(@NotNull @Valid String location) {

        return transactionsReportingServiceImpl.findTotalTransactionsByLocation(location);
    }
}