package springboot.bookstorecafe.controllers;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import springboot.bookstorecafe.services.ProductService;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class ProductImageController {

	@Autowired
	private ProductService productService;

	@PostMapping("/uploadImage")
	public ResponseEntity<?> uploadProductImage(@RequestParam Long idProduct,
			@RequestParam("file") MultipartFile file) {
		try {
			productService.uploadProductImage(idProduct, file);
			return ResponseEntity.ok().body("Image uploaded successfully");
		} catch (RuntimeException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
}
