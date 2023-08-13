package springboot.bookstorecafe.controllers;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.mapping.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import springboot.bookstorecafe.models.Coffee;
import springboot.bookstorecafe.models.Product;
import springboot.bookstorecafe.models.ProductType;
import springboot.bookstorecafe.repositories.FoodRepository;
import springboot.bookstorecafe.services.BookService;
import springboot.bookstorecafe.services.CoffeeService;
import springboot.bookstorecafe.services.FoodService;

@RestController
public class CoffeeController {

	@Autowired
	private CoffeeService coffeeService;
	@Autowired
	private FoodService foodService;
	@Autowired
	private BookService bookService;

	@GetMapping(value = "/coffee")
	public Iterable<Product> getCoffee(@RequestParam ProductType productType) {

		if (productType == ProductType.ALLPRODUCTS) {

			List<Product> allProducts = new ArrayList<>();
			addAllItemsToList(allProducts, coffeeService.findAllItems());
			addAllItemsToList(allProducts, foodService.findAllFoods());
			addAllItemsToList(allProducts, bookService.findAllItems());
			return allProducts;
		} else if (productType == ProductType.COFFEE) {
			return coffeeService.findAllCoffeeByType(productType);
		} else if (productType == ProductType.FOOD) {
			return foodService.findAllFoodsByType(productType);
		} else if (productType == ProductType.BOOK) {

			return bookService.findAllBooksByType(productType);
		}
		return null;
	}

	@PostMapping(value = "/newCoffee")
	public ResponseEntity<Coffee> newCoffee(@RequestBody Coffee newCoffee) {
		coffeeService.addCoffee(newCoffee);
		return ResponseEntity.ok(newCoffee);
	}

	@PutMapping(value = "/updateCoffee")
	public ResponseEntity<Coffee> updateCoffee(@RequestParam Long id, @RequestBody Coffee updateCoffee) {

		Product coffee = coffeeService.findById(id);
		updateCoffee.setIdProduct(coffee.getIdProduct());
		coffeeService.updateCoffee(updateCoffee);
		return ResponseEntity.ok(updateCoffee);
	}

	private void addAllItemsToList(List<Product> productList, Iterable<? extends Product> items) {
		for (Product item : items) {
			productList.add(item);
		}
	}
//	@DeleteMapping(value = "/deleteCoffee")
//	public ResponseEntity<Coffee> deleteCoffee(@RequestParam Long id) {
//
//		Product coffee = coffeeService.findById(id);
//		if (coffee != null) {
//			coffeeService.deleteCoffee(coffeeService.findById(id));
//			return ResponseEntity.noContent().build();
//		} else {
//			return ResponseEntity.notFound().build();
//		}
//	}
}