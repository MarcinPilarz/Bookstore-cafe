package springboot.bookstorecafe.models;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "role")
public class Role {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_role")
	private Long idRole;

	//@NotNull
	@Column(name = "role_type")
	private RoleType roleType;

	//@NotNull
	@OneToMany(mappedBy = "role")
	private List<Person> persons = new ArrayList<>();

	private enum RoleType {
		Klient, Pracownik, Wlasciciel
	}

	public Long getIdRole() {
		return idRole;
	}

	public void setIdRole(Long idRole) {
		this.idRole = idRole;
	}

	public RoleType getRoleType() {
		return roleType;
	}

	public void setRoleType(RoleType roleType) {
		this.roleType = roleType;
	}

	public List<Person> getPersons() {
		return persons;
	}

	public void setPersons(List<Person> persons) {
		this.persons = persons;
	}

	// tu będzie role_type sprawdzić czym jest enum w springu

}
