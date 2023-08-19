package springboot.bookstorecafe.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import springboot.bookstorecafe.models.Coffee;
import springboot.bookstorecafe.models.Product;
import springboot.bookstorecafe.models.ProductType;
import springboot.bookstorecafe.repositories.CoffeeRepository;
import springboot.bookstorecafe.repositories.ProductRepository;

@Service
public class CoffeeService {

	@Autowired
	private CoffeeRepository coffeeRepo;
	@Qualifier("coffeeRepository") // Wskaźnik na repozytorium Coffee
	@Autowired
	private ProductRepository productRepo;

	public Iterable<Coffee> findAllItems() {

		return coffeeRepo.findAll();
	}

	public Iterable<Product> findAllCoffeeByType(ProductType productType) {
		return productRepo.getProductsByProductType(productType);
	}

	public void addCoffee(Coffee coffee) {

		coffeeRepo.save(coffee);
	}

	public void deleteCoffee(Coffee product) {
		coffeeRepo.delete(product);

	}

	public void updateCoffee(Coffee coffee) {
		coffeeRepo.save(coffee);

	}
//
//	@Override
//	public Coffee findById(Long id) {
//		return coffeeRepo.findById(id).orElse(null);
//	}

//	public List<Coffee> getAllCoffee(ProductType productType) {
//	return coffeeRepo.getProductsByType();
//}

	public Product findById(Long id) {
		// TODO Auto-generated method stub
		return coffeeRepo.findById(id).orElse(null);
	}

//	public Coffee findByTypeOfCoffee(String typeOfCoffee) {
//		return coffeeRepo.findByTypeOfCoffee(typeOfCoffee);
//	}

}
