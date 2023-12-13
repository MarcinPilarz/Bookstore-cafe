package springboot.bookstorecafe.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import springboot.bookstorecafe.models.Book;

import springboot.bookstorecafe.models.Product;
import springboot.bookstorecafe.models.ProductType;
import springboot.bookstorecafe.services.BookService;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class BookController {

	@Autowired
	private BookService bookService;

	@PostMapping(value = "/addBook")
	public ResponseEntity<Book> addBook(@RequestParam ProductType bookType, @RequestBody Book newBook) {
		if (bookType == ProductType.BOOK) {
			bookService.addItem(newBook);
			return ResponseEntity.ok(newBook);
		}
		return null;
	}

	@PutMapping(value = "/updateBook")
	public ResponseEntity<Book> updateBook(@RequestParam Long id, @RequestBody Book updateBook) {

		Product book = bookService.findById(id);
		updateBook.setIdProduct(book.getIdProduct());
		bookService.updateItem(updateBook);
		return ResponseEntity.ok(updateBook);

	}

	@DeleteMapping(value = "/deleteBook")
	public ResponseEntity<Book> deleteBook(@RequestParam Long id) {
		Product book = bookService.findById(id);
		if (book != null) {
			bookService.deleteBook(id);
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}
}
