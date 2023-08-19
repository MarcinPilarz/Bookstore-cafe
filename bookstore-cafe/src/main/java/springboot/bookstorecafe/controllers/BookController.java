package springboot.bookstorecafe.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import springboot.bookstorecafe.models.Book;
import springboot.bookstorecafe.models.Food;
import springboot.bookstorecafe.services.BookService;

@RestController
public class BookController {

	@Autowired
	private BookService bookService;

//	@GetMapping(value = "/books")
//	public List<Book> getBooks() {
//
//		return bookService.findAllItems();
//	}
//
//	@PostMapping(value = "/addBook")
//	public ResponseEntity<Book> addBook(@RequestBody Book newBook) {
//		bookService.addItem(newBook);
//		return ResponseEntity.ok(newBook);
//	}
//
//	@PutMapping(value = "/updateBook")
//	public ResponseEntity<Book> updateBook(@RequestParam Long id, @RequestBody Book updateBook) {
//
//		Book book = bookService.findById(id);
//		updateBook.setIdBook(book.getIdBook());
//		bookService.updateItem(updateBook);
//		return ResponseEntity.ok(updateBook);
//
//	}
//
//	@DeleteMapping(value = "/deleteBook")
//	public ResponseEntity<Book> deleteBook(@RequestParam Long id) {
//		Book book = bookService.findById(id);
//		if (book != null) {
//			bookService.deleteItem(bookService.findById(id));
//			return ResponseEntity.noContent().build();
//		} else {
//			return ResponseEntity.notFound().build();
//		}
//	}
}
