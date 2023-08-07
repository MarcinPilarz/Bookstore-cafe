package springboot.bookstorecafe.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import springboot.bookstorecafe.models.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{

	Product findByProductName(String productName);
}
