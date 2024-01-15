package springboot.bookstorecafe.services;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import springboot.bookstorecafe.DTO.JwtAuthenticationDTO;
import springboot.bookstorecafe.DTO.PersonAndPersonLoginDTO;
import springboot.bookstorecafe.DTO.RefreshTokenDTO;
import springboot.bookstorecafe.models.LoginPerson;
import springboot.bookstorecafe.models.Person;
import springboot.bookstorecafe.models.RoleType;
import springboot.bookstorecafe.repositories.LoginPersonInterface;
import springboot.bookstorecafe.repositories.LoginPersonRepository;
import springboot.bookstorecafe.repositories.PersonRepository;

@Service
@RequiredArgsConstructor
public class LoginPersonService implements LoginPersonInterface, AuthenticationService {

	@Autowired
	private LoginPersonRepository loginRepo;

	@Autowired
	@Lazy
	private AuthenticationManager authenticationManager;

	@Autowired
	private PersonRepository personRepo;

	@Autowired
	private JWTService jwtService;

	@Override
	public UserDetailsService userDetailsService() {
		return new UserDetailsService() {

			@Override
			public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

				return loginRepo.findByEmail(username)
						.orElseThrow(() -> new UsernameNotFoundException("User not found"));
			}

		};
	}

	public JwtAuthenticationDTO signinDTO(LoginPerson loginPerson) {

		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginPerson.getEmail(), loginPerson.getPassword()));

		var user = loginRepo.findByEmail(loginPerson.getEmail())
				.orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));
		var idLoginPerson = user.getIdLoginPerson();
		var jwt = jwtService.generateToken(user);
		var refreshToken = jwtService.generateRefreshToken(new HashMap<>(), user);

		Person person = personRepo.findByLoginPerson_IdLoginPerson(idLoginPerson)
				.orElseThrow(() -> new IllegalArgumentException("Person not found"));
		JwtAuthenticationDTO jwtDTO = new JwtAuthenticationDTO();

		jwtDTO.setToken(jwt);
		jwtDTO.setRefreshToken(refreshToken);

		jwtDTO.setFirstName(person.getFirstName());
		jwtDTO.setLastName(person.getLastName());
		jwtDTO.setPhoneNumber(person.getPhoneNumber());
		jwtDTO.setIdPerson(person.getIdPerson());
		jwtDTO.setIdLoginPerson(idLoginPerson);
		jwtDTO.setEmail(user.getEmail());
		jwtDTO.setRoleType(user.getRoleType());
		return (jwtDTO);

	}

	public JwtAuthenticationDTO refreshToken(RefreshTokenDTO refreshTokenDTO) {
		String userEmail = jwtService.extractUserName(refreshTokenDTO.getToken());
		LoginPerson loginPerson = loginRepo.findByEmail(userEmail).orElseThrow();
		if (jwtService.isTokenValid(refreshTokenDTO.getToken(), loginPerson)) {
			var jwt = jwtService.generateToken(loginPerson);
			JwtAuthenticationDTO jwtDTO = new JwtAuthenticationDTO();

			jwtDTO.setToken(jwt);
			jwtDTO.setRefreshToken(refreshTokenDTO.getToken());
			return jwtDTO;
		}
		return null;
	}

	public void updateRoleType(Long id, LoginPerson loginPerson) {

		loginRepo.save(loginPerson);

	}

	public List<PersonAndPersonLoginDTO> getEmployeesByRole() {
		List<LoginPerson> loginPersons = loginRepo.findByRoleType(RoleType.Pracownik);
		List<PersonAndPersonLoginDTO> dtos = new ArrayList<>();

		for (LoginPerson loginPerson : loginPersons) {
			Optional<Person> personOpt = personRepo.findByLoginPerson_IdLoginPerson(loginPerson.getIdLoginPerson());

			if (personOpt.isPresent()) {
				Person person = personOpt.get();
				PersonAndPersonLoginDTO dto = new PersonAndPersonLoginDTO(

						person.getIdPerson(), person.getFirstName(), person.getLastName(), person.getPhoneNumber(),
						loginPerson.getIdLoginPerson(), loginPerson.getEmail(), loginPerson.getPassword(),
						loginPerson.getRoleType()

				);
				dtos.add(dto);
			}
		}
		return dtos;
	}

	public List<LoginPerson> findAllItems() {

		return loginRepo.findAll();
	}

	public void deleteItem(LoginPerson loginPerson) {
		loginRepo.delete(loginPerson);
	}

	public LoginPerson findById(Long id) {
		return loginRepo.findById(id).orElse(null);
	}

	public void addLoginPerson(LoginPerson loginPerson) {
		loginRepo.save(loginPerson);
	}

	public void updateItem(LoginPerson loginPerson) {
		loginRepo.save(loginPerson);

	}

}
