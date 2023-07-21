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

import springboot.bookstorecafe.models.Person;
import springboot.bookstorecafe.services.PersonService;

@RestController
public class PersonController {

	@Autowired
	private PersonService personService;

	@GetMapping(value = "/person")
	public List<Person> getPerson() {

		return personService.findAllItems();

	}

	@PostMapping(value = "/newPerson")
	public ResponseEntity<Person> addPerson(@RequestBody Person newPeson) {

		personService.addItem(newPeson);
		return ResponseEntity.ok(newPeson);
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
//	@GetMapping(value = "/personName")
//	public List<Person> getPersonName(@RequestParam("firstName") String firstName,
//			@RequestParam("lastName") String lastName) {
//
//		if(firstName !=null && lastName !=null) {
//		return personService.findByPersonName(firstName, lastName);
//		} else if(firstName!=null) {
//			return personService.findByFirstName(firstName);
//			
//		} else if (lastName!= null) {
//			return personService.findByLastName(lastName);
//		} else {
//			return null;
//		}
//	}
}
