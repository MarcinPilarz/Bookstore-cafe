package springboot.bookstorecafe.models;

import java.sql.Date;

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
	@Column(name = "table_number")
	private Integer tableNumber;

	// @NotNull
	@Column(name = "bokking_data")
	private Date bokkingData;

	// @NotNull
	@Column(name = "isReservation")
	private Boolean isReservation;

	@ManyToOne
	@JoinColumn(name = "id_person")
	// @MapsId
	private Person person;

	public Long getIdReservation() {
		return idReservation;
	}

	public void setIdReservation(Long idReservation) {
		this.idReservation = idReservation;
	}

	public Integer getTableNumber() {
		return tableNumber;
	}

	public void setTableNumber(Integer tableNumber) {
		this.tableNumber = tableNumber;
	}

	public Date getBokkingData() {
		return bokkingData;
	}

	public void setBokkingData(Date bokkingData) {
		this.bokkingData = bokkingData;
	}

	public Boolean getIsReservation() {
		return isReservation;
	}

	public void setIsReservation(Boolean isReservation) {
		this.isReservation = isReservation;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

}
