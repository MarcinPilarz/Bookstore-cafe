package springboot.bookstorecafe.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity 
@Table(name="person")
public class Person {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_person")
	private Long idPerson;
	
	@NotNull
	@Column(name="first_name")
	private String firstName;
	
	@NotNull
	@Column(name="last_name")
	private String lastName;
	
	@NotNull
	@Column(name="phone_number", length=9)
	private Integer phoneNumber;
	
	@NotNull
	@Column(name="email")
	private String email;
	
	@ManyToOne
	@JoinColumn(name="id_role")
	private Role role;
	
	//ZOBACZYC TO
	@OneToOne(mappedBy="person", cascade=CascadeType.ALL)
	private Reservation reservation;
	
	@OneToOne(mappedBy="person")
	private Review review;
	
	@OneToOne(mappedBy="person")
	private OrderItem orderItem;
	
	@OneToOne(mappedBy="person")
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Reservation getReservation() {
		return reservation;
	}

	public void setReservation(Reservation reservation) {
		this.reservation = reservation;
	}

	public Review getReview() {
		return review;
	}

	public void setReview(Review review) {
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
	
	

}
	