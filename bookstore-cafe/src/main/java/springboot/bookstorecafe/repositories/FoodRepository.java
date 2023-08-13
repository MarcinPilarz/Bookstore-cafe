package springboot.bookstorecafe.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import springboot.bookstorecafe.models.Food;

@Repository
public interface FoodRepository extends ProductRepository<Food> {

//	@Query("SELECT p FROM Food p WHERE p.productType = 'FOOD'")
//	List<Food> getFoodByType();
}
