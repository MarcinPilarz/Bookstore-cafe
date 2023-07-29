package springboot.bookstorecafe.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import springboot.bookstorecafe.models.BookTable;
import springboot.bookstorecafe.repositories.BookTableRepository;

@Service
public class BookTableService implements MainService<BookTable> {

	@Autowired
	private BookTableRepository bookTableRepo;

	@Override
	public List<BookTable> findAllItems() {
		return bookTableRepo.findAll();
	}

	@Override
	public void addItem(BookTable bookTable) {

		if(!isTableNumber(bookTable.getTableNumber()))
		{
			throw new IllegalArgumentException("This table number already exists");
		}
		bookTableRepo.save(bookTable);

	}

	@Override
	public void deleteItem(BookTable bookTable) {
		bookTableRepo.delete(bookTable);

	}

	@Override
	public void updateItem(BookTable bookTable) {
		if(!isTableNumber(bookTable.getTableNumber()))
		{
			throw new IllegalArgumentException("This table number already exists");
		}
		bookTableRepo.save(bookTable);

	}

	@Override
	public BookTable findById(Long id) {

		return bookTableRepo.findById(id).orElse(null);
	}

	public boolean isTableNumber(int tableNumber) {
	
		return bookTableRepo.findByTableNumber(tableNumber)==null;
	}
}
