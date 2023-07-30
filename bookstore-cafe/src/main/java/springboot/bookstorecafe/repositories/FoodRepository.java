package springboot.bookstorecafe.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import springboot.bookstorecafe.models.Food;

@Repository
public interface FoodRepository extends JpaRepository<Food, Long> {

}
