package springboot.bookstorecafe.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import springboot.bookstorecafe.models.LoginPerson;
import springboot.bookstorecafe.models.Person;
import springboot.bookstorecafe.repositories.LoginPersonRepository;
import springboot.bookstorecafe.services.LoginPersonService;
import springboot.bookstorecafe.services.PersonService;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class LoginPersonController {

	@Autowired
	private LoginPersonService loginService;

	@Autowired
	private PersonService personService;
	
	@Autowired 
	private LoginPersonRepository loginRepo;

	@Autowired
	private PasswordEncoder passwordEncoder;

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

	@PutMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestParam String email, @RequestParam String newPassword) {
        LoginPerson loginPerson = loginRepo.findByEmail(email).orElse(null);

        if (loginPerson == null) {
            return ResponseEntity.notFound().build();
        }

        String encodedPassword = passwordEncoder.encode(newPassword);
        loginPerson.setPassword(encodedPassword);
        loginRepo.save(loginPerson);

        return ResponseEntity.ok("Hasło zostało zresetowane.");
    }
	@DeleteMapping(value = "/deleteLogin")
	public ResponseEntity<LoginPerson> deleteLoginPerson(@RequestParam Long id) {

		loginService.deleteItem(loginService.findById(id));
		return ResponseEntity.noContent().build();
	}

}
