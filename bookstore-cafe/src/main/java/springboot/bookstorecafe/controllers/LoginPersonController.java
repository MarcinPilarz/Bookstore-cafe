package springboot.bookstorecafe.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import springboot.bookstorecafe.models.LoginPerson;
import springboot.bookstorecafe.models.Person;
import springboot.bookstorecafe.services.LoginPersonService;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class LoginPersonController {

	@Autowired
	private LoginPersonService loginService;

	@GetMapping(value = "/loginPerson")
	public List<LoginPerson> getLoginPerson() {
		return loginService.findAllItems();
	}

	@DeleteMapping(value = "/deleteLogin")
	public ResponseEntity<LoginPerson> deleteLoginPerson(@RequestParam Long id) {
		LoginPerson loginPerson = loginService.findById(id);
		loginService.deleteItem(loginService.findById(id));
		return ResponseEntity.noContent().build();
	}

}
