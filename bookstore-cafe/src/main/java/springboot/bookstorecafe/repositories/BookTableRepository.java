package springboot.bookstorecafe.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import springboot.bookstorecafe.models.BookTable;

@Repository
public interface BookTableRepository extends JpaRepository<BookTable, Long> {

	BookTable findByTableNumber(int tableNumber);
}
