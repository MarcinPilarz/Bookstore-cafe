package springboot.bookstorecafe.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import springboot.bookstorecafe.models.LoginPerson;
import springboot.bookstorecafe.models.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

//	void save(PersonAndPersonLoginDTO newPersonDTO);

//	@Query("SELECT p FROM Person p WHERE p.firstName= :firstName AND p.lastName= :lastName")
//	List<Person> findByPersonName(@Param("firstName") String firstName, @Param("lastName") String lastName);
//
//	List<Person> findByFirstName(String firstName);
//	
//	List<Person> findByLastName(String lastName);
	
	//List<Person> findByPersonName(String firstName, String lastName);
	
//	Optional <Person> findByLoginPerson(Long idLoginPerson);
	 Optional<Person> findByLoginPerson_IdLoginPerson(Long idLoginPerson);
}
