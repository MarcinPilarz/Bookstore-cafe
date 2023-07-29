package springboot.bookstorecafe.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import springboot.bookstorecafe.models.BookTable;
import springboot.bookstorecafe.models.Person;
import springboot.bookstorecafe.models.Reservation;
import springboot.bookstorecafe.repositories.BookTableRepository;
import springboot.bookstorecafe.repositories.PersonRepository;
import springboot.bookstorecafe.repositories.ReservationRepository;

@Service
@Transactional
public class ReservationService {

	private PersonRepository personRepo;
	private ReservationRepository reservationRepo;
	private BookTableRepository bookTableRepo;

	@Autowired
	public ReservationService(PersonRepository personRepo, ReservationRepository reservationRepo,
			BookTableRepository bookTableRepo) {

		this.personRepo = personRepo;
		this.reservationRepo = reservationRepo;
		this.bookTableRepo = bookTableRepo;
	}

	public List<Reservation> getReservations() {
		// TODO Auto-generated method stub
		return reservationRepo.findAll();
	}

	public void bookTable(Long idPerson, Long idBookTable, Long idReservation, LocalDate bokkingData,
			int numberOfPeople) {
		Person person = personRepo.findById(idPerson)
				.orElseThrow(() -> new RuntimeException("There is no such person: " + idPerson));

		BookTable bookTable = bookTableRepo.findById(idBookTable)
				.orElseThrow(() -> new RuntimeException("There is no such table: " + idBookTable));
		Reservation reservation = new Reservation();
		if (bookTable.isReservation()) {
			throw new RuntimeException("This table is reserved.");
		}

		reservation.setPerson(person);
		reservation.setBokkingData(bokkingData);
		reservation.setNumberOfPeople(numberOfPeople);
		reservation.setBookTable(bookTable);

		if (bookTable.getSeatingCapacity() < reservation.getNumberOfPeople()) {
			throw new RuntimeException("This table is not suitable for the stated number of people");
		}
		bookTable.setReservation(true);
		reservationRepo.save(reservation);
	}
	
	public void bookTableEmployee(Long idPerson, Long idBookTable, Long idReservation, LocalDate bokkingData,
			int numberOfPeople) {
		Person person = personRepo.findById(idPerson)
				.orElseThrow(() -> new RuntimeException("There is no such person: " + idPerson));

		BookTable bookTable = bookTableRepo.findById(idBookTable)
				.orElseThrow(() -> new RuntimeException("There is no such table: " + idBookTable));
		Reservation reservation = new Reservation();
		if (bookTable.isReservation()) {
			throw new RuntimeException("This table is reserved.");
		}

		reservation.setPerson(person);
		reservation.setBokkingData(bokkingData);
		reservation.setNumberOfPeople(numberOfPeople);
		reservation.setBookTable(bookTable);
		bookTable.setReservation(true);
		reservationRepo.save(reservation);
	}

	public void cancleReservation(Long idReservation) {
		Reservation reservation = reservationRepo.findById(idReservation)
				.orElseThrow(() -> new RuntimeException("Name reservation not found" + idReservation));

		if (!reservation.getBookTable().isReservation()) {

			throw new RuntimeException("The reservation is no longer active.");
		}

		reservation.getBookTable().setReservation(false);
		reservationRepo.delete(reservation);
	}
}
