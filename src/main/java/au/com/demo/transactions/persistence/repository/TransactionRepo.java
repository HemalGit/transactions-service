package au.com.demo.transactions.persistence.repository;

import au.com.demo.transactions.persistence.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;

public interface TransactionRepo extends JpaRepository<Transaction, Integer> {

    @Query(value = "SELECT tx.quantity * p.cost as totalTxCost FROM transactions.transactions tx INNER JOIN transactions.product p ON tx.product_code = p.product_code;", nativeQuery = true)
    List<BigDecimal> findTotalTransactionsCost();

    @Query(value = "SELECT tx.quantity * p.cost as totalTxCost FROM transactions.transactions tx INNER JOIN transactions.product p ON tx.product_code = p.product_code WHERE tx.customer_id = :customerId AND tx.product_code = :productCode;", nativeQuery = true)
    List<BigDecimal> findTotalTransactionsCostByCustomerIdAndProductCode(String customerId, String productCode);

    @Query(value = "SELECT tx.quantity * p.cost as totalTxCost FROM transactions.transactions tx INNER JOIN transactions.product p ON tx.product_code = p.product_code WHERE tx.customer_id = :customerId;", nativeQuery = true)
    List<BigDecimal> findTotalTransactionsCostByCustomerId(String customerId);

    @Query(value = "SELECT tx.quantity * p.cost as totalTxCost FROM transactions.transactions tx INNER JOIN transactions.product p ON tx.product_code = p.product_code WHERE tx.product_code = :productCode;", nativeQuery = true)
    List<BigDecimal> findTotalTransactionsCostByProductCode(String productCode);

    @Query(value = "SELECT count(tx.*) FROM transactions.transactions tx INNER JOIN transactions.customer c ON tx.customer_id = c.customer_id WHERE c.location = :location;", nativeQuery = true)
    Integer findTotalTransactionsByLocation(String location);
}
