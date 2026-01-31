package springboot.bookstorecafe.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import springboot.bookstorecafe.models.Coffee;
import springboot.bookstorecafe.models.Product;
import springboot.bookstorecafe.models.ProductType;

@Repository
public interface CoffeeRepository extends ProductRepository<Coffee> {
    //Coffee findByProductName(String productName);
	
}
