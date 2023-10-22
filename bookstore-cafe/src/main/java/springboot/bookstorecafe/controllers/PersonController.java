package springboot.bookstorecafe.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import springboot.bookstorecafe.DTO.PersonAndPersonLoginDTO;
import springboot.bookstorecafe.DTO.PersonDTO;
import springboot.bookstorecafe.models.LoginPerson;

import springboot.bookstorecafe.models.Person;

import springboot.bookstorecafe.services.LoginPersonService;
import springboot.bookstorecafe.services.PersonService;
import static springboot.bookstorecafe.DTO.mapper.PersonDTOMapper.mapPersonToPersonInfo;

@RestController
public class PersonController {

	@Autowired
	private PersonService personService;

	@Autowired
	private LoginPersonService loginService;

	@GetMapping(value = "/person")
	public List<Person> getPerson() {

		return personService.findAllItems();

	}

	@GetMapping(value = "/personDTO")
	public List<PersonDTO> getPersonDTO() {

		return mapPersonToPersonInfo(personService.findAllItems());

	}

	@PostMapping("/newPerson")
	public ResponseEntity<String> addItem(@RequestBody PersonAndPersonLoginDTO newPersonDTO) {
		Person person = new Person();
		person.setFirstName(newPersonDTO.firstName());
		person.setLastName(newPersonDTO.lastName());
		person.setPhoneNumber(newPersonDTO.phoneNumber());

		LoginPerson loginPerson = new LoginPerson();

		loginPerson.setEmail(newPersonDTO.email());
		loginPerson.setPassword(newPersonDTO.password());
		loginPerson.setRoleType(newPersonDTO.roleType());

		loginService.addLoginPerson(loginPerson);
		person.setLoginPerson(loginPerson);
		personService.addNewPerson(person);

		return ResponseEntity.ok("Okej");
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
			personService.deleteItem(personService.findById(id));
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}

}
