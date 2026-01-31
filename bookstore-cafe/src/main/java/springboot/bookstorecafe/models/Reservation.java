package springboot.bookstorecafe.models;


import java.time.LocalDate;

//import java.util.Date;  <-- Trzeba zobaczyć które jest poprawne 
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "reservation")
public class Reservation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_reservation")
	private Long idReservation;

	// @NotNull
//	@Column(name = "table_number")
//	private int tableNumber;

	// @NotNull
	@Column(name = "bokking_data")
	private LocalDate bokkingData;

	@Column(name="number_of_people")
	private int numberOfPeople;
	
	// @NotNull
//	@Column(name = "isReservation")
//	private boolean isReservation =false;

	
	
	@ManyToOne
	@JoinColumn(name = "id_person")
	// @MapsId
	private Person person;

	@ManyToOne
	@JoinColumn(name="id_book_table")
	private BookTable bookTable;
	
	public Long getIdReservation() {
		return idReservation;
	}

	public void setIdReservation(Long idReservation) {
		this.idReservation = idReservation;
	}

//	public int getTableNumber() {
//		return tableNumber;
//	}
//
//	public void setTableNumber(int tableNumber) {
//		this.tableNumber = tableNumber;
//	}

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

//	public boolean isReservation() {
//		return isReservation;
//	}
//
//	public void setReservation(boolean isReservation) {
//		this.isReservation = isReservation;
//	}

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
