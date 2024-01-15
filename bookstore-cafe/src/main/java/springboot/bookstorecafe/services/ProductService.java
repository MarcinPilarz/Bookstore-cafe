package springboot.bookstorecafe.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;

import jakarta.persistence.EntityNotFoundException;

import springboot.bookstorecafe.models.Book;
import springboot.bookstorecafe.models.Coffee;
import springboot.bookstorecafe.models.Food;
import springboot.bookstorecafe.models.Product;

import springboot.bookstorecafe.repositories.BookRepository;
import springboot.bookstorecafe.repositories.CoffeeRepository;
import springboot.bookstorecafe.repositories.FoodRepository;

@Service
public class ProductService {

	@Autowired
	private Storage storage;

	@Autowired
	private CoffeeRepository coffeeRepo;
	@Autowired
	private BookRepository bookRepo;
	@Autowired
	private FoodRepository foodRepo;

	public void uploadProductImage(Long idProduct, MultipartFile file) {
		String objectName = "springbootphoto/" + file.getOriginalFilename();

		try {
			BlobId blobId = BlobId.of("springbootphoto", objectName);
			BlobInfo blobinfo = BlobInfo.newBuilder(blobId).setContentType("image/jpeg").build();
			storage.create(blobinfo, file.getBytes());

			String imageUrl = "https://storage.googleapis.com/springbootphoto/" + file.getOriginalFilename();

			Product product = getProductByProductId(idProduct)
					.orElseThrow(() -> new RuntimeException("Product not found: " + idProduct));

			product.setImageName(file.getOriginalFilename());

			if (product instanceof Coffee) {
				coffeeRepo.save((Coffee) product);
			} else if (product instanceof Book) {
				bookRepo.save((Book) product);
			} else if (product instanceof Food) {
				foodRepo.save((Food) product);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Error uploading photo", e);
		}
	}

	private Optional<Product> getProductByProductId(Long productId) {

		Optional<? extends Product> product;

		product = coffeeRepo.findById(productId);
		if (product.isPresent()) {
			return Optional.of(product.get());
		}

		product = bookRepo.findById(productId);
		if (product.isPresent()) {
			return Optional.of(product.get());
		}

		product = foodRepo.findById(productId);
		if (product.isPresent()) {
			return Optional.of(product.get());
		}

		return Optional.empty();
	}

	public Product findById(Long id) {
		Optional<? extends Product> product;

		product = coffeeRepo.findById(id);
		if (product.isPresent()) {
			return product.get();
		}

		product = bookRepo.findById(id);
		if (product.isPresent()) {
			return product.get();
		}

		product = foodRepo.findById(id);
		if (product.isPresent()) {
			return product.get();
		}

		throw new RuntimeException("Product not found with id: " + id);
	}

	public void deleteProduct(Long id) {

		if (coffeeRepo.existsById(id)) {
			coffeeRepo.deleteById(id);
		} else if (bookRepo.existsById(id)) {
			bookRepo.deleteById(id);
		} else if (foodRepo.existsById(id)) {
			foodRepo.deleteById(id);
		} else {
			throw new EntityNotFoundException("Produkt o ID " + id + " nie został znaleziony");
		}
	}

}
