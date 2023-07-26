package springboot.bookstorecafe.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import springboot.bookstorecafe.models.Person;
import springboot.bookstorecafe.models.Reservation;
import springboot.bookstorecafe.repositories.PersonRepository;
import springboot.bookstorecafe.repositories.ReservationRepository;

@Service
@Transactional
public class ReservationService {

	private PersonRepository personRepo;
	private ReservationRepository reservationRepo;

	@Autowired
	public ReservationService(PersonRepository personRepo, ReservationRepository reservationRepo) {

		this.personRepo = personRepo;
		this.reservationRepo = reservationRepo;
	}

	public List<Reservation> getReservations() {
		// TODO Auto-generated method stub
		return reservationRepo.findAll();
	}

	public void bookTable(Long idPerson, Long idReservation, LocalDate bokkingData) {
		Person person = personRepo.findById(idPerson)
				.orElseThrow(() -> new RuntimeException("There is no such person: " + idPerson));
//		Reservation reservation = reservationRepo.findById(idReservation)
//				.orElseThrow(() -> new RuntimeException("This table is already booked: " + idReservation));

		Reservation reservation = new Reservation();
		if (reservation.isReservation()) {
			throw new RuntimeException("This table is reserved.");
		}
		reservation.getTableNumber();
		reservation.setReservation(true);

		reservation.setPerson(person);
		reservation.setBokkingData(bokkingData);

		reservationRepo.save(reservation);
	}

	public void cancleReservation(Long idReservation) {
		Reservation reservation = reservationRepo.findById(idReservation)
				.orElseThrow(() -> new RuntimeException("Name reservation not found" + idReservation));

		if (!reservation.isReservation()) {

			throw new RuntimeException("The reservation is no longer active.");
		}

		reservation.setReservation(false);
		reservationRepo.delete(reservation);
	}
}
