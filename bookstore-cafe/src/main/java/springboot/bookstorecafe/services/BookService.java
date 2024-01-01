package springboot.bookstorecafe.services;

import java.util.ArrayList;
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

//	public Iterable<Product> findAllBooksByType(ProductType productType) {
//		return productRepo.getProductsByProductType(productType);
//	}

	public List<Product> findAllBooksByType(ProductType productType) {
        if (productType == ProductType.BOOK) {
            return new ArrayList<>(bookRepo.getProductsByProductType(productType));
        }
        return new ArrayList<>();
    }
	
	public void addItem(Book book) {
		Book existingBook= bookRepo.findByProductName(book.getProductName());
		if(existingBook != null) {
			throw new IllegalArgumentException("Product with the same name already exists: " + book.getProductName());
		}
		bookRepo.save(book);

	}

	public List<Product> findAllBookByType(ProductType productType) {
        if (productType == ProductType.BOOK) {
            return new ArrayList<>(bookRepo.getProductsByProductType(productType));
        }
        return new ArrayList<>();
    }
	public void deleteBook(Long idBook) {

		bookRepo.deleteById(idBook);

	}

	public void updateItem(Book book) {
		bookRepo.save(book);

	}

	public Product findById(Long id) {

		return bookRepo.findById(id).orElse(null);
	}

	public void addAllItemsToList(List<Product> productList, Iterable<? extends Product> items) {
		for (Product item : items) {
			productList.add(item);
		}
	}
}
