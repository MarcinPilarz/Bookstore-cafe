package springboot.bookstorecafe.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import springboot.bookstorecafe.models.Book;
import springboot.bookstorecafe.models.Coffee;
import springboot.bookstorecafe.models.Food;
import springboot.bookstorecafe.models.Product;
import springboot.bookstorecafe.models.ProductType;
import springboot.bookstorecafe.services.CoffeeService;
import springboot.bookstorecafe.services.ProductService;

@RestController
public class ProductController {

	@Autowired
	private ProductService productService;

	@Autowired
	private CoffeeService coffeService;

	@GetMapping(value = "/products")
	public List<Product> getProducts() {
		return productService.findAllProducts();
	}

	@GetMapping(value = "/productName/{productName}")
	public ResponseEntity<Product> getProductName(@PathVariable String productName) {
		Product product = productService.findByProductName(productName);
		if (product == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(product);
	}

	@PostMapping(value = "/newProduct")
	public ResponseEntity<Product> addProduct(@RequestBody Product addProduct) {

		try {
			if (addProduct.getProductType() == ProductType.COFFEE) {

				Coffee newCoffee = new Coffee();
				
				
				Product newProduct = new Product();
				

				productService.addCoffee(newProduct, newCoffee);

			} else if (addProduct.getProductType() == ProductType.BOOK) {

				Product newProduct = new Product();
				Book newBook = new Book();

				productService.addBook(newProduct, newBook);

			} else if (addProduct.getProductType() == ProductType.FOOD) {

				Product newProduct= new Product();
				Food newFood= new Food();
				
				productService.addFood(newProduct, newFood);
			}
			System.out.print("błąd");
			return ResponseEntity.badRequest().build();
		} catch (Exception e) {
			System.out.print(e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
}
