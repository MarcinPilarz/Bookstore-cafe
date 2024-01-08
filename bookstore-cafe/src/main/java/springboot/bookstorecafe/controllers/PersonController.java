package springboot.bookstorecafe.controllers;

import java.util.Collection;
import java.util.HashMap; 
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.stripe.Stripe;

import springboot.bookstorecafe.DTO.JwtAuthenticationDTO;
import springboot.bookstorecafe.DTO.PersonAndPersonLoginDTO;
import springboot.bookstorecafe.DTO.PersonDTO;
import springboot.bookstorecafe.DTO.RefreshTokenDTO;
import springboot.bookstorecafe.models.LoginPerson;

import springboot.bookstorecafe.models.Person;
import springboot.bookstorecafe.models.RoleType;
import springboot.bookstorecafe.repositories.PersonRepository;
import springboot.bookstorecafe.services.AuthenticationService;
import springboot.bookstorecafe.services.LoginPersonService;
import springboot.bookstorecafe.services.PersonService;
import static springboot.bookstorecafe.DTO.mapper.PersonDTOMapper.mapPersonToPersonInfo;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class PersonController {

	
	
//	@Autowired
//	private AuthenticationManager authenticationManager;
	
	@Autowired
	private PersonService personService;

	@Autowired
	private LoginPersonService loginService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private AuthenticationService authenticationService;
	
	@Autowired
	private PersonRepository personRepo;
	String stripeKey;
	@GetMapping(value = "/person")
	public List<Person> getPerson() {

		return personService.findAllItems();

	}
	
	@GetMapping(value="/employees")
	 public ResponseEntity<List<PersonAndPersonLoginDTO>> getEmployees() {
        return ResponseEntity.ok(loginService.getEmployeesByRole());
    }

	@GetMapping(value = "/personDTO")
	public List<PersonDTO> getPersonDTO() {

		return mapPersonToPersonInfo(personService.findAllItems());

	}
	
	@GetMapping(value="/personDetails")
	public ResponseEntity<Person> getPersonDetails(@RequestParam Long id){
		 try {
	            Person person = personService.displayUserInfo(id);
	            return ResponseEntity.ok(person);
	        } catch (RuntimeException e) {
	            // Obsługa wyjątku, np. gdy osoba nie zostanie znaleziona
	            return ResponseEntity.notFound().build();
	        }
	    }
	
	
//.............
	//Logowanie
	@PostMapping("/signin")
	public ResponseEntity<JwtAuthenticationDTO> signin(@RequestBody LoginPerson loginPerson){
		return ResponseEntity.ok(authenticationService.signinDTO(loginPerson));
	}
	
	
	//refresh Token
		@PostMapping("/refresh")
		public ResponseEntity<JwtAuthenticationDTO> refresh(@RequestBody RefreshTokenDTO refreshTokenDTO){
			return ResponseEntity.ok(authenticationService.refreshToken(refreshTokenDTO));
		}
	
	
	//Rejestracja
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
		
		//loginPerson.(passwordEncoder.encode(newPersonDTO.g))
		loginPerson.setRoleType(newPersonDTO.roleType());

		
		loginService.addLoginPerson(loginPerson);
		person.setLoginPerson(loginPerson);
		personService.addNewPerson(person);
		personService.registerPeopleToStripe(person, loginPerson);

		return ResponseEntity.ok("Okej");
		} catch( Exception e){
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Wystąpił błąd podczas zapisywania użytkownika.");
			
		}
	}
	
	
	@PutMapping(value = "/editUser")
	public ResponseEntity<PersonAndPersonLoginDTO> editUser(@RequestParam Long id, @RequestBody PersonAndPersonLoginDTO updateUserDTO) {
	    // Znajdź istniejącego użytkownika na podstawie ID
	    Person person = personService.findById(id);
	    if (person == null) {
	        return ResponseEntity.notFound().build();
	    }

	    // Aktualizuj dostępne dane osoby
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

	    // Aktualizuj dane logowania
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

	    // Przygotuj DTO do odpowiedzi
	    PersonAndPersonLoginDTO responseDTO = new PersonAndPersonLoginDTO(
	       
	    	person.getIdPerson(),
	        person.getFirstName(),
	        person.getLastName(),
	        loginPerson != null ? loginPerson.getEmail() : null,
	       loginPersonId, 
	       person.getPhoneNumber(),
	        "Rola",
	        null
	    );

	    return ResponseEntity.ok(responseDTO);
	}




	@GetMapping("/sayAdmin")
	public ResponseEntity<String> sayHello(){
		return ResponseEntity.ok("Hi Pracownik");
	}
	
	@GetMapping("/sayUser")
	public ResponseEntity<String> sayHelloU(){
		return ResponseEntity.ok("Hi Pracownik");
	}
//...........................
//	@PostMapping(value="/loginPerson")
//	public ResponseEntity<LoginPerson> loginPerson (@RequestBody LoginPerson loginPerson, @AuthenticationPrincipal Authentication authentication){
//		
//		try {
//			
//			 // Uwierzytelnianie użytkownika za pomocą e-maila jako nazwy użytkownika
//			 String username = authentication.getName();
//		        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
//			
//			System.out.println(authentication);
//			
//			UserDetails userDetails = loginService.loadUserByUsername(loginPerson.getEmail());
//			String token = loginService.generateToken(userDetails);
//			
//			//String roleString = userDetails.getAuthorities().iterator().next().getAuthority();
//		//	RoleType roleType = RoleType.valueOf(roleString);
//			//RoleType roleType = (RoleType) userDetails.getAuthorities().iterator().next();
//			String roleString = userDetails.getAuthorities().iterator().next().getAuthority();
//
//			// Konwersja roli na odpowiednią wartość RoleType
//			RoleType roleType = RoleType.valueOf(roleString);
//			System.out.println("ROLA:      " + roleType);
//			
//			LoginPerson loggedInUser = loginService.getUserByUsername(loginPerson.getEmail());
//
//			loggedInUser.setRoleType(roleType);
//			loggedInUser.setToken(token);
//			// Zwracanie odpowiedzi z obiektem loggedInUser i statusem HTTP 200 OK
//		return ResponseEntity.ok(loggedInUser);
//		} catch(AuthenticationException e) {
//			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//		}
//	}
//	
	
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
			LoginPerson loginPerson=loginService.findById(person.getLoginPerson().getIdLoginPerson());
			
			personService.deleteItem(personService.findById(id));
			loginService.deleteItem(loginPerson);
			
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}

}
