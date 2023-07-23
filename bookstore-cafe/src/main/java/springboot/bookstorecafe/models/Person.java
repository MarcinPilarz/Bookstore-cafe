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
	private Integer phoneNumber;

	@OneToOne
	@JoinColumn(name = "id_login_person")
	private LoginPerson loginPerson;

	// ZOBACZYC TO
	@OneToOne(mappedBy = "person", cascade = CascadeType.ALL)
	private Reservation reservation;

	
	@OneToMany(mappedBy = "person", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<Review> review = new ArrayList<>();

	@OneToOne(mappedBy = "person")
	private OrderItem orderItem;

	@OneToOne(mappedBy = "person")
	private Event event;

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

	public Integer getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(Integer phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Reservation getReservation() {
		return reservation;
	}

	public LoginPerson getLoginPerson() {
		return loginPerson;
	}

	public void setLoginPerson(LoginPerson loginPerson) {
		this.loginPerson = loginPerson;
	}

	public void setReservation(Reservation reservation) {
		this.reservation = reservation;
	}

	public List<Review> getReview() {
		return review;
	}

	public void setReview(List<Review> review) {
		this.review = review;
	}

	public OrderItem getOrderItem() {
		return orderItem;
	}

	public void setOrderItem(OrderItem orderItem) {
		this.orderItem = orderItem;
	}

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	@Override
	public int hashCode() {
		return Objects.hash(idPerson);
	}

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

}
