package springboot.bookstorecafe.models;
import java.sql.Date;

//import java.util.Date;  <-- Trzeba zobaczyć które jest poprawne 
import jakarta.persistence.*;

@Entity
@Table(name="reservation")
public class Reservation {

	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id_reservation;
	
	@Column(name="table_number")
	private Integer table_number;
	
	@Column(name="bokking_data")
	private Date bokking_data;
	
	@Column(name="isReservation")
	private Boolean isReservation;

	public Long getId_reservation() {
		return id_reservation;
	}

	public void setId_reservation(Long id_reservation) {
		this.id_reservation = id_reservation;
	}

	public Integer getTable_number() {
		return table_number;
	}

	public void setTable_number(Integer table_number) {
		this.table_number = table_number;
	}

	public Date getBokking_data() {
		return bokking_data;
	}

	public void setBokking_data(Date bokking_data) {
		this.bokking_data = bokking_data;
	}

	public Boolean getIsReservation() {
		return isReservation;
	}

	public void setIsReservation(Boolean isReservation) {
		this.isReservation = isReservation;
	}
	
	
}

