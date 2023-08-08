package springboot.bookstorecafe.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import springboot.bookstorecafe.models.Book;
import springboot.bookstorecafe.models.Coffee;
import springboot.bookstorecafe.models.Food;
import springboot.bookstorecafe.models.Product;
import springboot.bookstorecafe.repositories.ProductRepository;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepo;

	public List<Product> findAllProducts() {
		return productRepo.findAll();
	}

	public Product addCoffee(Product product, Coffee coffee) {

		product.setCoffee(coffee);
		return productRepo.save(product);

	}

	public Product addBook(Product product, Book book) {

		product.setBook(book);
		return productRepo.save(product);
	}

	public Product addFood(Product product, Food food) {

		product.setFood(food);
		return productRepo.save(product);
	}

	public void deleteItem(Product product) {
		productRepo.delete(product);

	}

	public void updateItem(Product product) {
		productRepo.save(product);

	}

	public Product findById(Long id) {

		return productRepo.findById(id).orElse(null);
	}

	public Product findByProductName(String productName) {
		return productRepo.findByProductName(productName);
	}
}
