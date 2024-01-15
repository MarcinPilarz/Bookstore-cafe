package springboot.bookstorecafe.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import springboot.bookstorecafe.DTO.JwtAuthenticationDTO;
import springboot.bookstorecafe.DTO.PersonAndPersonLoginDTO;
import springboot.bookstorecafe.DTO.PersonDTO;
import springboot.bookstorecafe.DTO.RefreshTokenDTO;
import springboot.bookstorecafe.models.LoginPerson;

import springboot.bookstorecafe.models.Person;

import springboot.bookstorecafe.services.AuthenticationService;
import springboot.bookstorecafe.services.LoginPersonService;
import springboot.bookstorecafe.services.PersonService;
import static springboot.bookstorecafe.DTO.mapper.PersonDTOMapper.mapPersonToPersonInfo;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class PersonController {

	@Autowired
	private PersonService personService;

	@Autowired
	private LoginPersonService loginService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private AuthenticationService authenticationService;

	String stripeKey;

	@GetMapping(value = "/person")
	public List<Person> getPerson() {

		return personService.findAllItems();

	}

	@GetMapping(value = "/employees")
	public ResponseEntity<List<PersonAndPersonLoginDTO>> getEmployees() {
		return ResponseEntity.ok(loginService.getEmployeesByRole());
	}

	@GetMapping(value = "/personDTO")
	public List<PersonDTO> getPersonDTO() {

		return mapPersonToPersonInfo(personService.findAllItems());

	}

	@GetMapping(value = "/personDetails")
	public ResponseEntity<Person> getPersonDetails(@RequestParam Long id) {
		try {
			Person person = personService.displayUserInfo(id);
			return ResponseEntity.ok(person);
		} catch (RuntimeException e) {

			return ResponseEntity.notFound().build();
		}
	}

	@PostMapping("/signin")
	public ResponseEntity<JwtAuthenticationDTO> signin(@RequestBody LoginPerson loginPerson) {
		return ResponseEntity.ok(authenticationService.signinDTO(loginPerson));
	}

	@PostMapping("/refresh")
	public ResponseEntity<JwtAuthenticationDTO> refresh(@RequestBody RefreshTokenDTO refreshTokenDTO) {
		return ResponseEntity.ok(authenticationService.refreshToken(refreshTokenDTO));
	}

	@PostMapping("/newPerson")
	public ResponseEntity<String> addItem(@RequestBody PersonAndPersonLoginDTO newPersonDTO) {

		try {

			Person person = new Person();
			person.setFirstName(newPersonDTO.firstName());
			person.setLastName(newPersonDTO.lastName());
			person.setPhoneNumber(newPersonDTO.phoneNumber());

			LoginPerson loginPerson = new LoginPerson();

			loginPerson.setEmail(newPersonDTO.email());
			loginPerson.setPassword(newPersonDTO.password());
			String encodedPassword = passwordEncoder.encode(loginPerson.getPassword());
			loginPerson.setPassword(encodedPassword);

			loginPerson.setRoleType(newPersonDTO.roleType());

			loginService.addLoginPerson(loginPerson);
			person.setLoginPerson(loginPerson);
			personService.addNewPerson(person);
			personService.registerPeopleToStripe(person, loginPerson);

			return ResponseEntity.ok("Okej");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Wystąpił błąd podczas zapisywania użytkownika.");

		}
	}

	@PutMapping(value = "/editUser")
	public ResponseEntity<PersonAndPersonLoginDTO> editUser(@RequestParam Long id,
			@RequestBody PersonAndPersonLoginDTO updateUserDTO) {

		Person person = personService.findById(id);
		if (person == null) {
			return ResponseEntity.notFound().build();
		}

		if (updateUserDTO.firstName() != null && !updateUserDTO.firstName().isEmpty()) {
			person.setFirstName(updateUserDTO.firstName());
		}
		if (updateUserDTO.lastName() != null && !updateUserDTO.lastName().isEmpty()) {
			person.setLastName(updateUserDTO.lastName());
		}
		if (updateUserDTO.phoneNumber() != null && !updateUserDTO.phoneNumber().isEmpty()) {
			person.setPhoneNumber(updateUserDTO.phoneNumber());
		}

		personService.updateItem(person);

		var loginPersonId = person.getLoginPerson().getIdLoginPerson();
		LoginPerson loginPerson = loginService.findById(loginPersonId);
		if (loginPerson != null) {
			if (updateUserDTO.email() != null && !updateUserDTO.email().isEmpty()) {
				loginPerson.setEmail(updateUserDTO.email());
			}
			if (updateUserDTO.password() != null && !updateUserDTO.password().isEmpty()) {
				String encodedPassword = passwordEncoder.encode(updateUserDTO.password());
				loginPerson.setPassword(encodedPassword);
			}

			loginService.updateItem(loginPerson);
		} else {
			System.out.println("PersonLogin jest null");
		}

		PersonAndPersonLoginDTO responseDTO = new PersonAndPersonLoginDTO(

				person.getIdPerson(), person.getFirstName(), person.getLastName(),
				loginPerson != null ? loginPerson.getEmail() : null, loginPersonId, person.getPhoneNumber(), "Rola",
				null);

		return ResponseEntity.ok(responseDTO);
	}

	@PutMapping(value = "/editPerson")
	public ResponseEntity<Person> editPerson(@RequestParam Long id, @RequestBody Person updatePerson) {

		Person person = personService.findById(id);
		updatePerson.setIdPerson(person.getIdPerson());
		personService.updateItem(updatePerson);
		return ResponseEntity.ok(updatePerson);
	}

	@DeleteMapping(value = "/deletePerson")
	public ResponseEntity<Person> deletePerson(@RequestParam Long id) {
		Person person = personService.findById(id);

		if (person != null) {
			LoginPerson loginPerson = loginService.findById(person.getLoginPerson().getIdLoginPerson());

			personService.deleteItem(personService.findById(id));
			loginService.deleteItem(loginPerson);

			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}

}
