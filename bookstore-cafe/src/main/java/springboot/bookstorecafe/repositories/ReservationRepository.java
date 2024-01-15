package springboot.bookstorecafe.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import springboot.bookstorecafe.models.BookTable;
import springboot.bookstorecafe.models.Reservation;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

	List<Reservation> findByBookTableAndBokkingData(BookTable bookTable, LocalDate bokkingData);

	List<Reservation> findByBokkingDataBefore(LocalDate date);
}
