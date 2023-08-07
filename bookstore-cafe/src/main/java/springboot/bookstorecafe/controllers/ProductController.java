package springboot.bookstorecafe.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import springboot.bookstorecafe.models.Product;
import springboot.bookstorecafe.services.ProductService;

@RestController
public class ProductController {

	
	@Autowired
	private ProductService productService;
	
	@GetMapping(value="/products")
	public List<Product> getProducts(){
		return productService.findAllProducts();
	}
	
	@GetMapping(value="/productName/{productName}")
	public ResponseEntity<Product> getProductName(@PathVariable String productName){
		Product product= productService.findByProductName(productName);
		if(product ==null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(product);
	}
}
