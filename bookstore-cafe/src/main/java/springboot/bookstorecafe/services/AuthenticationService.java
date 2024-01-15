package springboot.bookstorecafe.services;

import springboot.bookstorecafe.DTO.JwtAuthenticationDTO;

import springboot.bookstorecafe.DTO.RefreshTokenDTO;
import springboot.bookstorecafe.models.LoginPerson;

public interface AuthenticationService {

	JwtAuthenticationDTO signinDTO(LoginPerson loginPerson);

	JwtAuthenticationDTO refreshToken(RefreshTokenDTO refreshTokenDTO);
}
