package springboot.bookstorecafe.repositories;

import org.springframework.stereotype.Repository;

import springboot.bookstorecafe.models.Food;

@Repository
public interface FoodRepository extends ProductRepository<Food> {

}
