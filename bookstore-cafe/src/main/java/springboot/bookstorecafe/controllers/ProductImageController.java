package springboot.bookstorecafe.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import springboot.bookstorecafe.DTO.ProductaAndProductPhotoInfoDTO;

import springboot.bookstorecafe.models.ProductType;

import springboot.bookstorecafe.services.ProductImageService;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class ProductImageController {

	@Autowired
	private ProductImageService imageService;

	@GetMapping("/images")
	public ResponseEntity<List<ProductaAndProductPhotoInfoDTO>> getAllImagesWithProducts(
			@RequestParam ProductType productType) {
		List<ProductaAndProductPhotoInfoDTO> imageInfoList = imageService.getAllImagesWithProducts(productType);
		return ResponseEntity.ok(imageInfoList);
	}

	@PostMapping(value = "/addImage/{idProduct}")
	public ResponseEntity<String> addImageProduct(@PathVariable Long idProduct, @RequestParam MultipartFile file) {
		try {
			imageService.uploadProductImage(idProduct, file);

			// Blob blob= new javax.sql.rowset.SerialBlob(bytes);

			return ResponseEntity.ok("Photo is added");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading photo");
		}
	}
}
