package springboot.bookstorecafe.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="book_table")
public class BookTable {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_book_table")
	private Long idBookTable;
	
	@Column(name = "table_number")
	private int tableNumber;
	
	
	@Column(name= "seating_capacity")
	private int seatingCapacity;
	
	@Column(name = "isReservation")
	private boolean isReservation;

	public Long getIdBookTable() {
		return idBookTable;
	}

	public void setIdBookTable(Long idBookTable) {
		this.idBookTable = idBookTable;
	}

	public int getTableNumber() {
		return tableNumber;
	}

	public void setTableNumber(int tableNumber) {
		this.tableNumber = tableNumber;
	}

	public int getSeatingCapacity() {
		return seatingCapacity;
	}

	public void setSeatingCapacity(int seatingCapacity) {
		this.seatingCapacity = seatingCapacity;
	}

	public boolean isReservation() {
		return isReservation;
	}

	public void setReservation(boolean isReservation) {
		this.isReservation = isReservation;
	}

	
	
}
