package springboot.bookstorecafe.models;

import java.util.Objects;

import jakarta.persistence.*;

@Entity
@Table(name = "login_person")
public class LoginPerson {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_login_person")
	private Long idLoginPerson;

	@Column(name = "email")
	private String email;

	@Column(name = "password")
	private String password;

	// @NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "role_type")
	private RoleType roleType;

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

}
