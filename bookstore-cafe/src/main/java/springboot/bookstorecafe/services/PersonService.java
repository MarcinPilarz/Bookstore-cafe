package springboot.bookstorecafe.services;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import springboot.bookstorecafe.DTO.PersonAndPersonLoginDTO;
import springboot.bookstorecafe.models.LoginPerson;
import springboot.bookstorecafe.models.Person;
//import springboot.bookstorecafe.models.LoginPerson.RoleType;
import springboot.bookstorecafe.repositories.LoginPersonRepository;
import springboot.bookstorecafe.repositories.PersonRepository;


@Service
public class PersonService implements MainService<Person> {

	@Autowired
	private PersonRepository personRepo;

	@Autowired 
	private LoginPersonRepository loginPersonRepo;
	
	
	@Override
	public List<Person> findAllItems() {

		return personRepo.findAll();
	}

	 
	
	public void registerPeople(Person person, LoginPerson loginPerson) {
		
	//person.setLoginPerson(loginPerson);
		//LoginPerson loginPerson= new LoginPerson();
		person.setLoginPerson(loginPerson);
		//loginPerson.setEmail(loginPerson);
		 String email=person.getLoginPerson().getEmail();
		loginPerson.setEmail(email);
		loginPersonRepo.save(loginPerson);
		personRepo.save(person);
		
		//loginPersonRepo.save(loginPerson);
		
		//person.setLoginPerson(loginPerson.getIdLoginPerson());
		//personRepo.save(person);
	}

	
//	 public ResponseEntity<String> addItem1(Person newPerson, LoginPerson loginPerson) {
//	        // Ustaw pole przyklad w encji ModelSamochodu
//	       // modelSamochodu.setPrzyklad(przyklad);
//	       // newPerson.setLoginPerson(loginPerson);
//	      //  LoginPerson loginPerson=new LoginPerson();
//	        
//	        
//	        newPerson.setLoginPerson(loginPerson);
//	        String email=newPerson.getLoginPerson().getEmail();
//	      
//	        loginPerson.setEmail(email);
//	        loginPersonRepo.save(loginPerson);
//	        personRepo.save(newPerson);
//	       
//	        return ResponseEntity.ok("Pomyślnie dodano element.");
//	    }
	
//	 public ResponseEntity<String> addItem1(PersonAndPersonLoginDTO newPersonDTO) {
//	        // Ustaw pole przyklad w encji ModelSamochodu
//	       // modelSamochodu.setPrzyklad(przyklad);
//	       // newPerson.setLoginPerson(loginPerson);
//	      //  LoginPerson loginPerson=new LoginPerson();
//	        
//	        
////	        newPerson.setLoginPerson(loginPerson);
////	        String email=newPerson.getLoginPerson().getEmail();
////	      
////	        loginPerson.setEmail(email);
////	        loginPersonRepo.save(loginPerson);
//	        personRepo.save(newPersonDTO);
//	       
//	        return ResponseEntity.ok("Pomyślnie dodano element.");
//	    }
//	
	public Person addNewPerson(Person person) {
		
		return personRepo.save(person);
	}
	@Override
	public void deleteItem(Person person) {
		personRepo.delete(person);

	}

	@Override
	public void updateItem(Person person) {
		personRepo.save(person);

	}

	@Override
	public Person findById(Long id) {
		return personRepo.findById(id).orElse(null);
	}



	@Override
	public void addItem(Person object) {
		// TODO Auto-generated method stub
		
	}

//	public List<Person> findByPersonName(String searchQuery){
//		return personRepo.findByPersonName(searchQuery, searchQuery);
//	}

//	public List<Person> findByLastName(String lastName) {
//	
//		return personRepo.findByLastName(lastName);
//	}
//
//	public List<Person> findByFirstName(String firstName) {
//		
//		return personRepo.findByFirstName(firstName);
//	}

}
