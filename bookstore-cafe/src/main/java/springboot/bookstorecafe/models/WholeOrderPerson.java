package springboot.bookstorecafe.models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class WholeOrderPerson {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_whole_order_person")
	private Long idWholeOrderPerson;

	@Column(name = "date_order")
	private LocalDateTime dateOrder;

	@Column(name = "total_price")
	private Double totalPrice;

	@Enumerated(EnumType.STRING)
	@Column(name = "order_status")
	private OrderStatus orderStatus;

	@ManyToOne
	@JoinColumn(name = "id_person")
	private Person person;

	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)

	private List<OrderProduct> order = new ArrayList<>();

	public Long getIdWholeOrderPerson() {
		return idWholeOrderPerson;
	}

	public void setIdWholeOrderPerson(Long idWholeOrderPerson) {
		this.idWholeOrderPerson = idWholeOrderPerson;
	}

	public LocalDateTime getDateOrder() {
		return dateOrder;
	}

	public void setDateOrder(LocalDateTime dateOrder) {
		this.dateOrder = dateOrder;
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

	public List<OrderProduct> getOrder() {
		return order;
	}

	public void setOrder(List<OrderProduct> order) {
		this.order = order;
	}

	public OrderStatus getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String displayStatus) {
		this.orderStatus = OrderStatus.fromDisplayStatus(displayStatus);
	}

}
