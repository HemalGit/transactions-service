package au.com.demo.transactions.persistence.repository;

import au.com.demo.transactions.persistence.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepo extends JpaRepository<Customer, Integer> {
}
