package springboot.bookstorecafe.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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

import springboot.bookstorecafe.services.BookService;
import springboot.bookstorecafe.services.CoffeeService;
import springboot.bookstorecafe.services.FoodService;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class CoffeeController {

	@Autowired
	private CoffeeService coffeeService;
	@Autowired
	private FoodService foodService;
	@Autowired
	private BookService bookService;

//	@GetMapping(value = "/coffee")
//	public Iterable<Product> getCoffee(@RequestParam ProductType productType) {
//
//		if (productType == ProductType.ALLPRODUCTS) {
//
//			List<Product> allProducts = new ArrayList<>();
//			coffeeService.addAllItemsToList(allProducts, coffeeService.findAllItems());
//			foodService.addAllItemsToList(allProducts, foodService.findAllFoods());
//			bookService.addAllItemsToList(allProducts, bookService.findAllItems());
//			return allProducts;
//		} else if (productType == ProductType.COFFEE) {
//			return coffeeService.findAllCoffeeByType(productType);
//		} else if (productType == ProductType.FOOD) {
//			return foodService.findAllFoodsByType(productType);
//		} else if (productType == ProductType.BOOK) {
//
//			return bookService.findAllBooksByType(productType);
//		}
//		return null;
//	}
	
	@GetMapping(value = "/products")
	public ResponseEntity<List<Product>> getProductsByTypes(@RequestParam ProductType productType) {
	    List<Product> products;

	    if (productType == ProductType.ALLPRODUCTS) {
	        products = Stream.of(
	            coffeeService.findAllCoffeeByType(ProductType.COFFEE),
	            bookService.findAllBookByType(ProductType.BOOK),
	            foodService.findAllFoodByType(ProductType.FOOD)
	        ).flatMap(List::stream).collect(Collectors.toList());
	    } else {
	        products = getProductsByType(productType);
	    }

	    products.forEach(product -> {
	        if (product.getImageName() != null) {
	            product.setImageName("https://storage.googleapis.com/springbootphoto/springbootphoto/" + product.getImageName());
	        }
	    });

	    return ResponseEntity.ok(products);
	}
	    private List<Product> getProductsByType(ProductType productType) {
	        switch (productType) {
	            case COFFEE:
	                return coffeeService.findAllCoffeeByType(productType);
	            case BOOK:
	                return bookService.findAllBookByType(productType);
	            case FOOD:
	                return foodService.findAllFoodByType(productType);
	            default:
	                return List.of();
	        }
	    }


	@PostMapping(value = "/newCoffee")
	public ResponseEntity<Coffee> newCoffee(@RequestParam ProductType coffeeType, @RequestBody Coffee newCoffee) {
		if (coffeeType == ProductType.COFFEE ) {

			newCoffee.setProductType(coffeeType);
			coffeeService.addCoffee(newCoffee);
			return ResponseEntity.ok(newCoffee);
		}
		return null;
	}

	@PutMapping(value = "/updateCoffee")
	public ResponseEntity<Coffee> updateCoffee(@RequestParam Long id, @RequestBody Coffee updateCoffee) {

		Product coffee = coffeeService.findById(id);
		updateCoffee.setIdProduct(coffee.getIdProduct());
		coffeeService.updateCoffee(updateCoffee);
		return ResponseEntity.ok(updateCoffee);
	}

	@DeleteMapping(value = "/deleteCoffee")
	public ResponseEntity<Coffee> deleteCoffee(@RequestParam Long id) {

		Product coffee = coffeeService.findById(id);
		if (coffee != null) {

			coffeeService.deleteCoffee(id);
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}
}