package springboot.bookstorecafe.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import springboot.bookstorecafe.models.Book;
import springboot.bookstorecafe.models.Product;
import springboot.bookstorecafe.models.ProductType;
import springboot.bookstorecafe.repositories.BookRepository;
import springboot.bookstorecafe.repositories.ProductRepository;

@Service
public class BookService {

	@Autowired
	private BookRepository bookRepo;
	@Qualifier("bookRepository") // Wskaźnik na repozytorium Coffee
    @Autowired
    private ProductRepository productRepo;
	public Iterable<Book> findAllItems() {

		return bookRepo.findAll();
	}
//
//	public Iterable<Product> findAllBooksByType(ProductType productType) {
//
//		return bookRepo.getProductsByType(ProductType.BOOK);
//
//	}
	public Iterable<Product> findAllBooksByType(ProductType productType) {
        return productRepo.getProductsByProductType(productType);
    }
	public void addItem(Book book) {
		bookRepo.save(book);

	}

	public void deleteItem(Book book) {

		bookRepo.delete(book);

	}

	public void updateItem(Book book) {
		bookRepo.save(book);

	}

//	public Product findById(Long id) {
//
//		return bookRepo.findById(id).orElse(null);
//	}

}
