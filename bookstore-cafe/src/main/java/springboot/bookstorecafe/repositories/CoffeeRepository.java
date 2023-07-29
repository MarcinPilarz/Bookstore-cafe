package springboot.bookstorecafe.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import springboot.bookstorecafe.models.Coffee;

@Repository
public interface CoffeeRepository extends JpaRepository<Coffee, Long> {

	
	Coffee findByTypeOfCoffee(String typeOfCoffee);
}
