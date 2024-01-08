package springboot.bookstorecafe.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
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

		return reservationRepo.findAll();
	}
	
	public List<Reservation> getPersonReservations(Long personId) {
		Person person = personRepo.findById(personId)
				.orElseThrow(() -> new RuntimeException("There is no such person: " + personId));
		
		return person.getReservations();
	}

	public void bookTable(Long idPerson, LocalDate bookingData, int numberOfPeople) {
		Person person = personRepo.findById(idPerson)
				.orElseThrow(() -> new RuntimeException("There is no such person: " + idPerson));

		 List<BookTable> availableTables = bookTableRepo.findAvailableTablesByDate(bookingData); // Zakładam, że taka metoda istnieje
		    if (availableTables.isEmpty()) {
		        throw new RuntimeException("There are no available tables for the specified date.");
		    }

		    // Losowy wybór stolika
		    Random random = new Random();
		    BookTable bookTable = availableTables.get(random.nextInt(availableTables.size()));
		Reservation reservation = new Reservation();
		if (bookTable.isReservation()) {
			throw new RuntimeException("This table is reserved.");
		}

		
		List<Reservation> existingReservations = reservationRepo.findByBookTableAndBokkingData(bookTable, bookingData);
		if (!existingReservations.isEmpty()) {
			throw new RuntimeException("This table is already reserved for the specified date.");
		}
		
		reservation.setPerson(person);
		reservation.setBokkingData(bookingData);
		reservation.setNumberOfPeople(numberOfPeople);
		reservation.setBookTable(bookTable);

		if (bookTable.getSeatingCapacity() < reservation.getNumberOfPeople()) {
			throw new RuntimeException("This table is not suitable for the stated number of people");
		}
		//bookTable.setReservation(true);
		

	    bookTable.setReservationDate(bookingData);
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


//		if (!reservation.getPerson().getLastName().equals(lastName)
//				|| !reservation.getPerson().getPhoneNumber().equals(phoneNumber)) {
//
//			throw new RuntimeException("Wrong last name or phone number! ");
//		}

		reservation.getBookTable().setReservation(false);
		reservationRepo.delete(reservation);
	}

	public void customEditionSeating(Reservation reservation) {
		
		reservationRepo.save(reservation);
	}
	
	public Reservation findById (Long idReservation) {
		return reservationRepo.findById(idReservation).orElse(null);
	}
	@Scheduled(cron = "0 0 0 * * 1") // Każdy poniedziałek o północy
    public void deleteOldReservations() {
        LocalDate oneWeekAgo = LocalDate.now().minusWeeks(1);
        List<Reservation> oldReservations = reservationRepo.findByBokkingDataBefore(oneWeekAgo);
        reservationRepo.deleteAll(oldReservations);
    }
}
