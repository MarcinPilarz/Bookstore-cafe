package springboot.bookstorecafe.services;

import springboot.bookstorecafe.DTO.JwtAuthenticationDTO;
import springboot.bookstorecafe.DTO.PersonAndPersonLoginDTO;
import springboot.bookstorecafe.DTO.RefreshTokenDTO;
import springboot.bookstorecafe.models.LoginPerson;

public interface AuthenticationService {

	//LoginPerson addLoginPerson(LoginPerson loginPerson);
	JwtAuthenticationDTO signinDTO(LoginPerson loginPerson);
	
	JwtAuthenticationDTO refreshToken(RefreshTokenDTO refreshTokenDTO);
}
