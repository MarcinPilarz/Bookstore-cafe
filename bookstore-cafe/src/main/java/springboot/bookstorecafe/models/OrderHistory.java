package springboot.bookstorecafe.models;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name= "order_history")
public class OrderHistory {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_order_history")
	private Long idOrderHistory;
	
	
	@Column(name= "date_order")
	private LocalDateTime dateOrder;


	@Column(name= "quantity")
	private  int quantity;
	
	@Column(name="total_price")
	private Double totalPrice;
	
	@ManyToOne
	@JoinColumn(name="id_person")
	private Person person;

	@ManyToOne
	@JoinColumn(name="id_product")
	private Product product;
	
	public Long getIdOrderHistory() {
		return idOrderHistory;
	}

	public void setIdOrderHistory(Long idOrderHistory) {
		this.idOrderHistory = idOrderHistory;
	}

	public LocalDateTime getDateOrder() {
		return dateOrder;
	}

	public void setDateOrder(LocalDateTime dateOrder) {
		this.dateOrder = dateOrder;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	
	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
	
	
	
}
