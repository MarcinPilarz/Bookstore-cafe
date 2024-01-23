package springboot.bookstorecafe.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import springboot.bookstorecafe.models.BookTable;

@Repository
public interface BookTableRepository extends JpaRepository<BookTable, Long> {

	BookTable findByTableNumber(int tableNumber);

	@Query("SELECT bt FROM BookTable bt WHERE bt.id NOT IN "
			+ "(SELECT res.bookTable.id FROM Reservation res WHERE res.bokkingData = :bokkingData)")
	List<BookTable> findAvailableTablesByDate(@Param("bokkingData")LocalDate bokkingData);
}
