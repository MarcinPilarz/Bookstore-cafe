package springboot.bookstorecafe.DTO;

public class JwtAuthenticationDTO {

	
	String token;
	String refreshToken;
Long  idLoginPerson;
Long idPerson;
String firstName;
String lastName;
String phoneNumber;
	


	
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
	public Long getIdLoginPerson() {
		return idLoginPerson;
	}
	public void setIdLoginPerson(Long idLoginPerson) {
		this.idLoginPerson = idLoginPerson;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getRefreshToken() {
		return refreshToken;
	}
	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
	
	
	
}
