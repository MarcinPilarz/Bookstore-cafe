package springboot.bookstorecafe.DTO.mapper;

import java.util.List;
import java.util.stream.Collectors;

import springboot.bookstorecafe.DTO.PersonDTO;
import springboot.bookstorecafe.models.Person;

public class PersonDTOMapper {

	public static List<PersonDTO> mapPersonToPersonInfo(List<Person> person) {
		return person.stream().map(personDTO -> new PersonDTO(personDTO.getIdPerson(), personDTO.getFirstName(),
				personDTO.getLastName(), personDTO.getPhoneNumber())).collect(Collectors.toList());
	}
}
