package springboot.bookstorecafe.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import springboot.bookstorecafe.models.LoginPerson;
import springboot.bookstorecafe.models.Person;
import springboot.bookstorecafe.services.LoginPersonService;
import springboot.bookstorecafe.services.PersonService;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class LoginPersonController {

	@Autowired
	private LoginPersonService loginService;

	@Autowired
	private PersonService personService;

	@GetMapping(value = "/loginPerson")
	public List<LoginPerson> getLoginPerson() {
		return loginService.findAllItems();
	}

	@PutMapping(value = "/updateRoleType")
	public ResponseEntity<Person> editRoleType(@RequestParam Long id, @RequestBody LoginPerson editRoleTypePerson) {

		Person person = personService.findById(id);

		LoginPerson loginPerson = person.getLoginPerson();

		loginPerson.setRoleType(editRoleTypePerson.getRoleType());

		loginService.updateRoleType(id, loginPerson);

		return ResponseEntity.ok(person);
	}

	@DeleteMapping(value = "/deleteLogin")
	public ResponseEntity<LoginPerson> deleteLoginPerson(@RequestParam Long id) {

		loginService.deleteItem(loginService.findById(id));
		return ResponseEntity.noContent().build();
	}

}
