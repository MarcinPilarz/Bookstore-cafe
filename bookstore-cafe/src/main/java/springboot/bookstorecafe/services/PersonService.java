package springboot.bookstorecafe.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import springboot.bookstorecafe.models.Person;
import springboot.bookstorecafe.repositories.PersonRepository;

@Service
public class PersonService implements MainService<Person> {

	@Autowired
	private PersonRepository personRepo;

	@Override
	public List<Person> findAllItems() {

		return personRepo.findAll();
	}

	@Override
	public void addItem(Person person) {
		personRepo.save(person);
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
