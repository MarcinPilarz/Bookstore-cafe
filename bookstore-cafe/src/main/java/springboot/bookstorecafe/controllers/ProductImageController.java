package springboot.bookstorecafe.controllers;

import java.io.IOException;   
import com.google.cloud.storage.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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
import springboot.bookstorecafe.models.Product;
import springboot.bookstorecafe.models.ProductImage;
import springboot.bookstorecafe.repositories.BookRepository;
import springboot.bookstorecafe.repositories.CoffeeRepository;
import springboot.bookstorecafe.repositories.FoodRepository;
import springboot.bookstorecafe.repositories.ProductRepository;
import springboot.bookstorecafe.services.ProductImageService;

@RestController
public class ProductImageController {

	@Autowired
	private ProductImageService imageService;

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
	
	
	
//	@GetMapping("/productImages")
//	public ResponseEntity<List<ProductInfo>> getAllProductImages() {
//	    List<ProductInfo> productInfos = new ArrayList<>();
//
//	    List<Product> products = productRepository.findAll();
//
//	    for (Product product : products) {
//	        ProductImage productImage = imageRepo.findByProductId(product.getId());
//
//	        if (productImage != null) {
//	            try {
//	                BlobId blobId = BlobId.of("springbootphoto", "springbootphoto/" + product.getId() + "/" + productImage.getImageName());
//	                Blob blob = storage.get(blobId);
//	                byte[] imageBytes = blob.getContent();
//
//	                ProductInfo productInfo = new ProductInfo();
//	                productInfo.setProduct(product);
//	                productInfo.setImageData(imageBytes);
//
//	                productInfos.add(productInfo);
//	            } catch (Exception e) {
//	                e.printStackTrace();
//	            }
//	        }
//	    }
//
//	    return new ResponseEntity<>(productInfos, HttpStatus.OK);
//	}
	
	
//	@GetMapping("/allProducts")
//	public List<Product> getAllProductsInfo() {
//	    List<Product> products = new ArrayList<>();
//
//	    // Dodaj wszystkie produkty z różnych repozytoriów do listy 'products'
//	    List<ProductRepository<? extends Product>> repositories = List.of(coffeeRepo, bookRepo, foodRepo);
//
//	    for (ProductRepository<? extends Product> repository : repositories) {
//	        products.addAll(StreamSupport.stream(repository.findAll().spliterator(), false)
//	            .collect(Collectors.toList()));
//	    }
//
//
//	    for (Product product : products) {
//	        ProductImage productImage = imageService.findById(product.getIdProduct());
//
//	        if (productImage != null) {
//	            try {
//	                // Pobierz obraz z Google Cloud Storage i ustaw go w encji Product
//	                BlobId blobId = BlobId.of("springbootphoto", "springbootphoto/" + product.getIdProduct() + "/" + productImage.getImageName());
//	                Blob blob = storage.get(blobId);
//	                byte[] imageBytes = blob.getContent();
//	                product.setProductImage(imageBytes);
//	            } catch (Exception e) {
//	                e.printStackTrace();
//	            }
//	        }
//	    }
//
//	    return products;
//	}

	
//	@GetMapping("getPhoto/{idProduct}/{imageName}")
//    public ResponseEntity<byte[]> getProductImage(@PathVariable Long idProduct, @PathVariable String imageName) {
//        try {
//            byte[] imageBytes = imageService.getProductImage(idProduct, imageName);
//            HttpHeaders headers = new HttpHeaders();
//            headers.setContentType(MediaType.IMAGE_JPEG_VALUE);
//
//            return new ResponseEntity<byte[]>(imageBytes, HttpStatus.OK);
//
//
//        } catch (IOException e) {
//            e.printStackTrace();
//            // Możesz obsłużyć błąd w dowolny sposób, na przykład zwrócić pusty obrazek lub odpowiedź z błędem.
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }
	
	
	
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
	    public ResponseEntity<List<String>> getAllImages1() {
	        List<String> imageUrls = new ArrayList<>();

	        // Przy użyciu Google Cloud Storage API uzyskaj listę obrazów w swoim buckecie
	        // i wygeneruj ich adresy URL

	        // Przykładowy sposób pobrania listy obrazów
	        Storage storage = StorageOptions.getDefaultInstance().getService();
	        String bucketName = "springbootphoto"; // Zmień na nazwę swojego bucketa
	        Page<Blob> blobs = storage.list(bucketName);

	        for (Blob blob : blobs.iterateAll()) {
	            String imageUrl = "https://storage.googleapis.com/" + bucketName + "/" + blob.getName();
	            imageUrls.add(imageUrl);
	        }

	        return ResponseEntity.ok(imageUrls);
	    }
	    
	@PostMapping(value="/addImage/{idProduct}")
	public ResponseEntity<String> addImageProduct(@PathVariable Long idProduct,@RequestParam MultipartFile file) {
		try {
		imageService.uploadProductImage(idProduct, file);
		
		//Blob blob= new javax.sql.rowset.SerialBlob(bytes);
		
		return ResponseEntity.ok("Photo is added");
		}catch( Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading photo");
		}
	}
}
