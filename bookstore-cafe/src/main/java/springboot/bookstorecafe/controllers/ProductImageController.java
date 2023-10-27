package springboot.bookstorecafe.controllers;

import java.io.IOException;
import com.google.cloud.storage.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.sql.rowset.serial.SerialException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.google.api.gax.paging.Page;
import com.google.api.client.http.HttpHeaders;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

import org.springframework.http.MediaType;

//import com.google.api.services.storage.Storage;
//import com.google.cloud.storage.BlobId;

import jakarta.servlet.http.HttpServletRequest;
import springboot.bookstorecafe.DTO.ProductPhotoInfo;
import springboot.bookstorecafe.models.Product;
import springboot.bookstorecafe.models.ProductImage;
import springboot.bookstorecafe.models.ProductType;
import springboot.bookstorecafe.repositories.BookRepository;
import springboot.bookstorecafe.repositories.CoffeeRepository;
import springboot.bookstorecafe.repositories.FoodRepository;
import springboot.bookstorecafe.repositories.ProductImageRepository;
import springboot.bookstorecafe.repositories.ProductRepository;
import springboot.bookstorecafe.services.ProductImageService;

@RestController
public class ProductImageController {

	@Autowired
	private ProductImageService imageService;

	@Autowired
	private ProductImageRepository imageRepo;
	@Autowired
	private Storage storage;

//	@GetMapping(value="/images")
//	public String sendData() {
//		BlobId id=BlobId.of("springbootphoto", null);
//	}
	@Autowired
	private CoffeeRepository coffeeRepo;
	@Autowired
	private BookRepository bookRepo;
	@Autowired
	private FoodRepository foodRepo;

	@GetMapping("/allImages")
	public ResponseEntity<List<String>> getAllImages() {
		String bucketName = "springbootphoto"; // Zmień na nazwę swojego "bucketa"
		List<String> imageUrls = new ArrayList<>();

		// Pobierz listę wszystkich obiektów w "buckecie"
		Iterable<Blob> blobs = storage.list(bucketName).iterateAll();
		for (Blob blob : blobs) {
			BlobId blobId = blob.getBlobId();
			String objectName = blobId.getName();

			// Tutaj możesz dowolnie formatować URL do obiektu w Cloud Storage
			// Oto prosty przykład, zakładając, że serwer Spring działa na localhost:8080
			String imageUrl = "http://localhost:8080/getPhoto/" + objectName;
			imageUrls.add(imageUrl);
		}

		return new ResponseEntity<>(imageUrls, HttpStatus.OK);
	}

	@GetMapping("/getPhoto/{imageName:.+}")
	public ResponseEntity<byte[]> getProductImage(@PathVariable String imageName) throws IOException {
		BlobId blobId = BlobId.of("springbootphoto", imageName); // Zmień na nazwę swojego "bucketa"
		Blob blob = storage.get(blobId);

		if (blob != null) {
			return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(blob.getContent());
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/images")
	public ResponseEntity<List<ProductPhotoInfo>> getAllImagesWithProducts(@RequestParam ProductType productType) {
		List<ProductPhotoInfo> imageInfoList = new ArrayList<>();

		List<ProductImage> productImages = imageRepo.findAll();

		for (ProductImage productImage : productImages) {
			Optional<Product> productOptional = getProductByProductId(productImage.getProduct().getIdProduct());

			if (productOptional.isPresent()) {
				Product product = productOptional.get();

				if (productType == ProductType.ALLPRODUCTS || product.getProductType() == productType) {
					String imageUrl = "https://storage.googleapis.com/springbootphoto/springbootphoto" + "/"
							+ productImage.getImageName();

					ProductPhotoInfo imageInfo = new ProductPhotoInfo(imageUrl, product);
					imageInfoList.add(imageInfo);
				}
			}
		}

		return ResponseEntity.ok(imageInfoList);
	}

	private Optional<Product> getProductByProductId(Long productId) {
		List<ProductRepository<? extends Product>> repositories = List.of(coffeeRepo, bookRepo, foodRepo);

		for (ProductRepository<? extends Product> repository : repositories) {
			Optional<? extends Product> product = repository.findById(productId);
			if (product.isPresent()) {
				return Optional.of(product.get());
			}
		}

		return Optional.empty();
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
