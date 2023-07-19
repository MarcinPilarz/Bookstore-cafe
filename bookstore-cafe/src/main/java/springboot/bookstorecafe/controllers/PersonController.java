package springboot.bookstorecafe.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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
