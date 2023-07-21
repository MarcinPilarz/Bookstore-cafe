package springboot.bookstorecafe.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "login_person")
public class LoginPerson {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_login_person")
	private Long idLoginPerson;

	@Column(name="email")
	private String email;
	
	@Column(name="password")
	private String password;
	
	//@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "role_type")
	private RoleType roleType;

	//@NotNull
	@OneToOne(mappedBy = "loginPerson")
    private Person person;

	private enum RoleType {
		Klient, Pracownik, Wlasciciel
	}

	

	public Long getIdLoginPerson() {
		return idLoginPerson;
	}

	public void setIdLoginPerson(Long idLoginPerson) {
		this.idLoginPerson = idLoginPerson;
	}

	public RoleType getRoleType() {
		return roleType;
	}

	public void setRoleType(RoleType roleType) {
		this.roleType = roleType;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public int hashCode() {
		return Objects.hash(idLoginPerson);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LoginPerson other = (LoginPerson) obj;
		return Objects.equals(idLoginPerson, other.idLoginPerson);
	}
	

//	public List<Person> getPersons() {
//		return persons;
//	}
//
//	public void setPersons(List<Person> persons) {
//		this.persons = persons;
//	}

	// tu będzie role_type sprawdzić czym jest enum w springu

}
