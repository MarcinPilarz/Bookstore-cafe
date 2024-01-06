package springboot.bookstorecafe.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import springboot.bookstorecafe.DTO.ProductDTO;
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

//	public List<Product> findAllCoffeeByType(ProductType productType) {
//		return productRepo.getProductsByProductType(productType);
//	}
//	private List<Product> getProductsByType(ProductType productType) {
//	    switch (productType) {
//	        case COFFEE:
//	            return coffeeService.findAllCoffeeByType(productType);
//	        case FOOD:
//	            return foodService.findAllFoodsByType(productType);
//	        case BOOK:
//	            return bookService.findAllBooksByType(productType);
//	        default:
//	            return new ArrayList<>();
//	    }
//	}
	public void addCoffee(Coffee coffee) {

		Coffee existingCoffee = coffeeRepo.findByProductName(coffee.getProductName());
		if (existingCoffee != null) {
			throw new IllegalArgumentException("Product with the same name already exists: " + coffee.getProductName());
		}
		coffee.setProductName(changingCoffeeNameSize(coffee.getProductName()));
		coffeeRepo.save(coffee);

	}
	
	
	public Coffee mapToCoffee(ProductDTO coffeeDTO) {
		 Coffee coffee = new Coffee();
		    coffee.setProductName(coffeeDTO.getProductName());
		    coffee.setProductPrice(coffeeDTO.getProductPrice());
		    coffee.setProductDescription(coffeeDTO.getProductDescription());
		    coffee.setProductType(coffeeDTO.getProductType());
		    coffee.setCoffeeIntensity(coffeeDTO.getCoffeeIntensity());
		    // Ustaw inne specyficzne pola dla Coffee, jeśli są dostępne
		    return coffee;
	}
	
	
	public List<Product> findAllCoffeeByType(ProductType productType) {
        if (productType == ProductType.COFFEE) {
            return new ArrayList<>(coffeeRepo.getProductsByProductType(productType));
        }
        return new ArrayList<>();
    }

	public void deleteCoffee(Long idCoffee) {
		coffeeRepo.deleteById(idCoffee);

	}

	public void updateCoffee(Coffee coffee) {

		coffee.setProductName(changingCoffeeNameSize(coffee.getProductName()));
		coffeeRepo.save(coffee);

	}

	public Product findById(Long id) {

		return coffeeRepo.findById(id).orElse(null);
	}

	public void addAllItemsToList(List<Product> productList, Iterable<? extends Product> items) {
		for (Product item : items) {
			productList.add(item);
		}
	}

	public static String changingCoffeeNameSize(String input) {
		if (input == null || input.isEmpty()) {
			return input;
		}

		return input.substring(0, 1).toUpperCase() + input.substring(1).toLowerCase();
	}
}
