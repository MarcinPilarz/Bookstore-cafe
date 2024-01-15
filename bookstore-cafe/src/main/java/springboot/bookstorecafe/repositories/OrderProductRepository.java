package springboot.bookstorecafe.repositories;

import org.springframework.data.jpa.repository.JpaRepository; 
import org.springframework.stereotype.Repository;

//import springboot.bookstorecafe.models.OrderItem;
import springboot.bookstorecafe.models.OrderProduct;

@Repository
public interface OrderProductRepository extends JpaRepository<OrderProduct, Long> {

}
