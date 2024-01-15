package springboot.bookstorecafe.models;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "login_person")
public class LoginPerson implements UserDetails, Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_login_person")
	private Long idLoginPerson;

	@NotNull
	@Column(name = "email")
	private String email;

	@NotNull
	@Column(name = "password")
	private String password;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "role_type")
	private RoleType roleType;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		if (roleType != null) {

			return List.of(new SimpleGrantedAuthority(roleType.name()));
		} else {

			return Collections.emptyList();
		}
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

	@Override
	public String getUsername() {
		// zwraca aktualna nazwe uzytkownika

		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		// Sprawdzenie daty wygasniecia konta
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// sprawdzenie czy konto jest zablokowoane
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true; // true =nie jest
	}

	@Override
	public boolean isEnabled() {
		// ssprawdzenie czy konto jest wlaczone
		return true;
	}

}
