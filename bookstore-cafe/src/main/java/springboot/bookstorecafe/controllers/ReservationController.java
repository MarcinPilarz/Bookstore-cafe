package springboot.bookstorecafe.controllers;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import springboot.bookstorecafe.DTO.ReservationDTO;
import springboot.bookstorecafe.models.Reservation;
import springboot.bookstorecafe.services.ReservationService;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class ReservationController {

	@Autowired
	private ReservationService reservationService;

	@GetMapping("/reservations")
	public List<Reservation> getReservations() {
		return reservationService.getReservations();
	}

	@GetMapping("/reservations/person")
	public ResponseEntity<List<Reservation>> getPersonReservations(@RequestParam Long personId) {
		try {
			List<Reservation> reservations = reservationService.getPersonReservations(personId);
			return ResponseEntity.ok(reservations);
		} catch (RuntimeException e) {

			return ResponseEntity.notFound().build();
		}
	}

	@PostMapping("/newReservation")
	public void bookTable(@RequestParam Long idPerson,
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate bokkingData,
			@RequestParam int numberOfPeople) {

		reservationService.bookTable(idPerson, bokkingData, numberOfPeople);
	}

	@PostMapping("/newReservationEmployee")
	public void bookTableEmployee(@RequestParam Long idPerson, @RequestParam Long idBookTable,
			@RequestParam Long idReservation,
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate bokkingData,
			@RequestParam int numberOfPeople) {

		reservationService.bookTableEmployee(idPerson, idBookTable, idReservation, bokkingData, numberOfPeople);
	}

	@PutMapping(value = "/customNumberOfPeople")
	public ResponseEntity<String> customEdit(@RequestParam Long idReservation,
			@RequestBody ReservationDTO customNumberOfPeople) {
		Reservation reservation = reservationService.findById(idReservation);

		reservation.setNumberOfPeople(customNumberOfPeople.numberOfPeople());

		reservationService.customEditionSeating(reservation);
		return ResponseEntity.ok("The seating has been successfully updated. ");
	}

	@DeleteMapping("/cancleReservation")
	public void cancleReservation(@RequestParam Long idReservation) {

		reservationService.cancleReservation(idReservation);
	}
}
