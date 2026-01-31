package springboot.bookstorecafe.controllers;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import springboot.bookstorecafe.models.Reservation;
import springboot.bookstorecafe.services.ReservationService;

@RestController
public class ReservationController {

	@Autowired
	private ReservationService reservationService;

	@GetMapping("/reservations")
	public List<Reservation> getReservations() {
		return reservationService.getReservations();
	}

	@PostMapping("/newReservation")
	public void bookTable(@RequestParam Long idPerson, @RequestParam Long idBookTable, @RequestParam Long idReservation,
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate bokkingData,
			@RequestParam int numberOfPeople) {

		reservationService.bookTable(idPerson, idBookTable, idReservation, bokkingData, numberOfPeople);
	}

	@PostMapping("/newReservationEmployee")
	public void bookTableEmployee(@RequestParam Long idPerson, @RequestParam Long idBookTable,
			@RequestParam Long idReservation,
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate bokkingData,
			@RequestParam int numberOfPeople) {

		reservationService.bookTableEmployee(idPerson, idBookTable, idReservation, bokkingData, numberOfPeople);
	}

	@DeleteMapping("/cancleReservation")
	public void cancleReservation(Long idReservation, String lastName, String phoneNumber) {

		reservationService.cancleReservation(idReservation, lastName, phoneNumber);
	}
}
