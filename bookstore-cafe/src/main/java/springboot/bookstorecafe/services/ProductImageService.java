package springboot.bookstorecafe.services;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List; 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

import springboot.bookstorecafe.models.Product;
import springboot.bookstorecafe.models.ProductImage;
import springboot.bookstorecafe.repositories.BookRepository;
import springboot.bookstorecafe.repositories.CoffeeRepository;
import springboot.bookstorecafe.repositories.FoodRepository;
import springboot.bookstorecafe.repositories.ProductImageRepository;
import springboot.bookstorecafe.repositories.ProductRepository;

@Service
public class ProductImageService {

	
	@Autowired
	private ProductImageRepository imageRepo;
	
	@Autowired
	private Storage storage;
	
	@Autowired 
	private CoffeeRepository coffeeRepo;
	@Autowired
	private BookRepository bookRepo;
	@Autowired
	private FoodRepository foodRepo;
	
	
	
	public ProductImage createImage(ProductImage image) {
		
		return imageRepo.save(image);
	}
	
	
	 public byte[] getProductImage(Long idProduct, String imageName) throws IOException {
	        String objectName = "springbootphoto/" + idProduct + "/" + imageName;
	        BlobId blobId = BlobId.of("springbootphoto", objectName);

	        Blob blob = storage.get(blobId);

	        if (blob != null) {
	            return blob.getContent();
	        } else {
	            // Zwracamy pusty obrazek lub obsługujemy inny błąd w zależności od potrzeb
	            return new byte[0];
	        }
	    }
	
	
	public void uploadProductImage(Long idProduct, MultipartFile file)  {
		String objectName= "springbootphoto/"+ file.getOriginalFilename();
		
		try {
			
//			Credentials credentials= GoogleCredentials
//					.fromStream(new FileInputStream("C:\\Users\\marci\\Dropbox\\Komputer\\Desktop\\Klucze\\client_secret_44916352466-dadgmq3mbs4njqd65fho6tsglvsqfgpi.apps.googleusercontent.com.json"));
//				Storage storage=StorageOptions.newBuilder().setCredentials(credentials)
//						.setProjectId("our-pursuit-403118").build().getService();
//			return storage;
			BlobId blobId=BlobId.of("springbootphoto", objectName);
		BlobInfo blobinfo= BlobInfo.newBuilder(blobId)
				.setContentType("image/jpeg")
				.build();
		
				 storage.create(blobinfo, file.getBytes());
		ProductImage productImage= new ProductImage();
		productImage.setImageName(file.getOriginalFilename());
		
		Product products = null;

		List<ProductRepository<? extends Product>> repositories = List.of(coffeeRepo, bookRepo, foodRepo);

		for (ProductRepository<? extends Product> repository : repositories) {
			products = repository.findById(idProduct).orElse(null);
			if (products != null) {
				break; // Znaleziono produkt w jednym z repozytoriów, przerywamy pętlę
			}
		}

		productImage.setProduct(products);
		imageRepo.save(productImage);
		}catch( Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Error uploading photo");
		}
		
	}
	public List<ProductImage> getAllImages(){
		
		return imageRepo.findAll();
	}
	
	
	public ProductImage findById(Long id) {
		return imageRepo.findById(id).get();
	}
}
