package springboot.bookstorecafe.DTO;

import springboot.bookstorecafe.models.RoleType;

public record PersonAndPersonLoginDTO(Long idPerson, String firstName, String lastName, String phoneNumber,
		Long idPersonLogin, String email, String password, RoleType roleType) {


}
