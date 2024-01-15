package springboot.bookstorecafe.repositories;

import org.springframework.stereotype.Repository;

import springboot.bookstorecafe.models.Coffee;

@Repository
public interface CoffeeRepository extends ProductRepository<Coffee> {

}
