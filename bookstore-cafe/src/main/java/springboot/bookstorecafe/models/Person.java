package springboot.bookstorecafe.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "person")
public class Person {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_person")
	private Long idPerson;

	// @NotNull
	@Column(name = "first_name")
	private String firstName;

	// @NotNull
	@Column(name = "last_name")
	private String lastName;

	// @NotNull
	@Column(name = "phone_number", length = 9)
	private String phoneNumber;

	@OneToOne
	@JoinColumn(name = "id_login_person")
	private LoginPerson loginPerson;

//	// ZOBACZYC TO
//	@OneToOne(mappedBy = "person", cascade = CascadeType.ALL)
//	private Reservation reservation;

	@OneToMany(mappedBy = "person")
	@JsonIgnore
	private List<Reservation> reservations;

	@OneToMany(mappedBy = "person", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<Review> review = new ArrayList<>();

//	@OneToOne(mappedBy = "person")
//	@JsonIgnore
//	private OrderItem orderItem;

	@OneToMany(mappedBy = "person", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<Event> event = new ArrayList<>();

	@OneToMany(mappedBy = "person", fetch = FetchType.LAZY)
	@JsonIgnore
	  private List<OrderItem> orderItems= new ArrayList<>();
	
	@OneToMany(mappedBy = "person",fetch = FetchType.EAGER)
	@JsonIgnore
	  private List<OrderHistory> orderHistory= new ArrayList<>();
//	
//	@ManyToMany
//    @JoinTable(
//        name = "person_orderitem", // Nazwa tabeli łączącej
//        joinColumns = @JoinColumn(name = "person_id"),
//        inverseJoinColumns = @JoinColumn(name = "orderitem_id")
//    )
//    private List<OrderItem> orderItems;
	public Long getIdPerson() {
		return idPerson;
	}

	public void setIdPerson(Long idPerson) {
		this.idPerson = idPerson;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public LoginPerson getLoginPerson() {
		return loginPerson;
	}

	public void setLoginPerson(LoginPerson loginPerson) {
		this.loginPerson = loginPerson;
	}

	public List<Reservation> getReservations() {
		return reservations;
	}

	public void setReservations(List<Reservation> reservations) {
		this.reservations = reservations;
	}

	public List<Review> getReview() {
		return review;
	}

	public void setReview(List<Review> review) {
		this.review = review;
	}

//	public OrderItem getOrderItem() {
//		return orderItem;
//	}
//
//	public void setOrderItem(OrderItem orderItem) {
//		this.orderItem = orderItem;
//	}

	public List<Event> getEvent() {
		return event;
	}

	public void setEvent(List<Event> event) {
		this.event = event;
	}

	@Override
	public int hashCode() {
		return Objects.hash(idPerson);
	}

	
	
	
	public List<OrderItem> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(List<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}

//	public List<OrderItem> getOrderItems() {
//		return orderItems;
//	}
//
//	public void setOrderItems(List<OrderItem> orderItems) {
//		this.orderItems = orderItems;
//	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Person other = (Person) obj;
		return Objects.equals(idPerson, other.idPerson);
	}

	public List<OrderHistory> getOrderHistory() {
		return orderHistory;
	}

	public void setOrderHistory(List<OrderHistory> orderHistory) {
		this.orderHistory = orderHistory;
	}

}
