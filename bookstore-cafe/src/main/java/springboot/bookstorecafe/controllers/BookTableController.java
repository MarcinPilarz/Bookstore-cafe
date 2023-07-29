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

import springboot.bookstorecafe.models.BookTable;
import springboot.bookstorecafe.models.Person;
import springboot.bookstorecafe.services.BookTableService;

@RestController
public class BookTableController {

	@Autowired
	private BookTableService bookTableService;

	@GetMapping(value = "/tables")
	public List<BookTable> getTables() {
		return bookTableService.findAllItems();
	}

	@PostMapping(value = "/addTable")
	public ResponseEntity<BookTable> addTable(@RequestBody BookTable bookTable) {

		bookTableService.addItem(bookTable);
		return ResponseEntity.ok(bookTable);

	}
	
	@PutMapping(value="/editTable")
	public ResponseEntity<BookTable> editTable(@RequestParam Long id, @RequestBody BookTable updateBookTable){
		
		BookTable bookTable= bookTableService.findById(id);
		updateBookTable.setIdBookTable(bookTable.getIdBookTable());
		bookTableService.updateItem(updateBookTable);
		return ResponseEntity.ok(updateBookTable);
	}
	
	
	@DeleteMapping(value="/deleteTable")
	public ResponseEntity<BookTable> editTable(@RequestParam Long id){
		BookTable bookTable= bookTableService.findById(id);
		
		if (bookTable != null) {
			bookTableService.deleteItem(bookTableService.findById(id));
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}
}
