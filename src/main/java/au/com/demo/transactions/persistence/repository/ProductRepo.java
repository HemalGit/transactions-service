package au.com.demo.transactions.persistence.repository;

import au.com.demo.transactions.persistence.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepo extends JpaRepository<Product, Integer> {

    Product findByProductCode(final String productCode);
}
