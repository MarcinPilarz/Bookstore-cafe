package springboot.bookstorecafe.models;

import java.time.LocalDate;

import jakarta.persistence.*;

@Entity
@Table(name = "reservation")
public class Reservation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_reservation")
	private Long idReservation;

	@Column(name = "bokking_data")
	private LocalDate bokkingData;

	@Column(name = "number_of_people")
	private int numberOfPeople;

	@ManyToOne
	@JoinColumn(name = "id_person")

	private Person person;

	@ManyToOne
	@JoinColumn(name = "id_book_table")
	private BookTable bookTable;

	public Long getIdReservation() {
		return idReservation;
	}

	public void setIdReservation(Long idReservation) {
		this.idReservation = idReservation;
	}

	public LocalDate getBokkingData() {
		return bokkingData;
	}

	public void setBokkingData(LocalDate bokkingData) {
		this.bokkingData = bokkingData;
	}

	public int getNumberOfPeople() {
		return numberOfPeople;
	}

	public void setNumberOfPeople(int numberOfPeople) {
		this.numberOfPeople = numberOfPeople;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public BookTable getBookTable() {
		return bookTable;
	}

	public void setBookTable(BookTable bookTable) {
		this.bookTable = bookTable;
	}

}
