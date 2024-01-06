package springboot.bookstorecafe.controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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

import springboot.bookstorecafe.DTO.ProductDTO;
import springboot.bookstorecafe.models.Book;
import springboot.bookstorecafe.models.Coffee;
import springboot.bookstorecafe.models.Food;
import springboot.bookstorecafe.models.Product;
import springboot.bookstorecafe.models.ProductType;

import springboot.bookstorecafe.services.BookService;
import springboot.bookstorecafe.services.CoffeeService;
import springboot.bookstorecafe.services.FoodService;
import springboot.bookstorecafe.services.ProductService;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class CoffeeController {

	@Autowired
	private CoffeeService coffeeService;
	@Autowired
	private FoodService foodService;
	@Autowired
	private BookService bookService;

	@Autowired
	private ProductService productService;
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
	        products = new ArrayList<>();
	        products.addAll(coffeeService.findAllCoffeeByType(ProductType.COFFEE));
	        products.addAll(bookService.findAllBookByType(ProductType.BOOK));
	        products.addAll(foodService.findAllFoodByType(ProductType.FOOD));
	    } else  {
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

	    
	    @PostMapping(value = "/addProduct")
	    public ResponseEntity<?> addProduct(@RequestBody ProductDTO productDTO) {
	        switch (productDTO.getProductType()) {
	            case COFFEE:
	                Coffee coffee = coffeeService.mapToCoffee(productDTO);
	                coffeeService.addCoffee(coffee);
	                return ResponseEntity.ok(coffee);
	            case FOOD:
	                Food food = foodService.mapToFood(productDTO);
	                foodService.addFood(food);
	                return ResponseEntity.ok(food);
	            case BOOK:
	                Book book = bookService.mapToBook(productDTO);
	                bookService.addItem(book);
	                return ResponseEntity.ok(book);
	            default:
	                throw new IllegalArgumentException("Nieznany typ produktu");
	        }
	    }
	    
	    @PutMapping(value="/updateProducts")
	    public ResponseEntity<?> updateProduct(@RequestParam Long id, @RequestBody ProductDTO productDTO) {
	        Product product = productService.findById(id);

	        if (product.getProductType() == ProductType.COFFEE) {
	            Coffee coffee = (Coffee) product;
	            // Ustawienie pól wspólnych
	            updateCommonProductFields(coffee, productDTO);
	            // Ustawienie pól specyficznych dla Coffee
	            coffee.setCoffeeIntensity(productDTO.getCoffeeIntensity());
	            coffeeService.updateCoffee(coffee);
	        } else if (product.getProductType() == ProductType.BOOK) {
	            Book book = (Book) product;
	            // Ustawienie pól wspólnych
	            updateCommonProductFields(book, productDTO);
	            // Ustawienie pól specyficznych dla Book
	            book.setAuthor(productDTO.getAuthor());
	            book.setGenere(productDTO.getGenere());
	            // ...inne pola dla Book
	            bookService.updateItem(book);
	        } else if (product.getProductType() == ProductType.FOOD) {
	            Food food = (Food) product;
	            // Ustawienie pól wspólnych
	            updateCommonProductFields(food, productDTO);
	            // Ustawienie pól specyficznych dla Food
	            food.setFoodWeight(productDTO.getFoodWeight());
	            food.setAmountOfCalories(productDTO.getAmountOfCalories());
	            foodService.updateFood(food);
	        } else {
	            throw new IllegalArgumentException("Nieznany typ produktu");
	        }

	        return ResponseEntity.ok().build();
	    }
	    
	    
	    private void updateCommonProductFields(Product product, ProductDTO dto) {
	        product.setProductName(dto.getProductName());
	        product.setProductPrice(dto.getProductPrice());
	        product.setProductDescription(dto.getProductDescription());
	        // Ustaw inne wspólne pola
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