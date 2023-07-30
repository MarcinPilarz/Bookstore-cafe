package springboot.bookstorecafe.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import springboot.bookstorecafe.models.Book;
import springboot.bookstorecafe.repositories.BookRepository;

@Service
public class BookService implements MainService<Book> {

	@Autowired
	private BookRepository bookRepo;

	@Override
	public List<Book> findAllItems() {

		return bookRepo.findAll();
	}

	@Override
	public void addItem(Book book) {
		bookRepo.save(book);

	}

	@Override
	public void deleteItem(Book book) {

		bookRepo.delete(book);

	}

	@Override
	public void updateItem(Book book) {
		bookRepo.save(book);

	}

	@Override
	public Book findById(Long id) {

		return bookRepo.findById(id).orElse(null);
	}

}
