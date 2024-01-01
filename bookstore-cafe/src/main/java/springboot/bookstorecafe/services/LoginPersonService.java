package springboot.bookstorecafe.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import springboot.bookstorecafe.JwtAuthenticationFilter;
import springboot.bookstorecafe.DTO.JwtAuthenticationDTO;
import springboot.bookstorecafe.DTO.PersonAndPersonLoginDTO;
import springboot.bookstorecafe.DTO.RefreshTokenDTO;
import springboot.bookstorecafe.models.LoginPerson;
import springboot.bookstorecafe.models.Person;
import springboot.bookstorecafe.repositories.LoginPersonInterface;
import springboot.bookstorecafe.repositories.LoginPersonRepository;
import springboot.bookstorecafe.repositories.PersonRepository;

@Service
@RequiredArgsConstructor
public class LoginPersonService implements LoginPersonInterface, AuthenticationService {

	// , AuthenticationService
	@Autowired
	private LoginPersonRepository loginRepo;

//	@Autowired
//	private LoginPersonInterface loginPersonInterface;
//	

	// private PasswordEncoder passwordEncoder;

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
				// TODO Auto-generated method stub
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
	// ............................................
//	@Override
//	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//		LoginPerson loginPerson = loginRepo.findByEmail(email);
//		if (loginPerson == null) {
//			throw new UsernameNotFoundException("Invalid email or password.");
//		}
//		List<GrantedAuthority> authorities = new ArrayList<>();
//		authorities.add(new SimpleGrantedAuthority(loginPerson.getRoleType().toString()));
//		return new User(loginPerson.getEmail(), loginPerson.getPassword(), authorities);
//	}

//	private PasswordEncoder passwordEncoder() {
//		/**
//		 * Tworzy nowy PasswordEncoder (BCryptPasswordEncoder).
//		 *
//		 * @return nowy PasswordEncoder
//		 */
//		return new BCryptPasswordEncoder();
//	}

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

//	public String generateToken(UserDetails userDetails) {
//
//		SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
//
//		Date now = new Date();
//		Date expirationDate = new Date(now.getTime() + 86400000); // Token wygasa po 24 godzinach
//
//		return Jwts.builder().setSubject(userDetails.getUsername()).signWith(key).compact();
//
//	}

//	private boolean passwordMatches(String rawPassword, String encodedPassword) {
//
//		return passwordEncoder().matches(rawPassword, encodedPassword);
//	}

//	public String loginUser(String email, String password) {
//
//		UserDetails userDetails = loadUserByUsername(email);
//
//		if (!passwordMatches(password, userDetails.getPassword())) {
//			throw new BadCredentialsException("Nieprawidłowe hasło");
//		}
//
//		// Generuj token JWT
//		String token = generateToken(userDetails);
//
//		return token;
//	}
//	
//	public LoginPerson getUserByUsername(String email) throws UsernameNotFoundException {
//		LoginPerson loginPerson = loginRepo.findByEmail(email);
//		if (loginPerson == null) {
//			throw new UsernameNotFoundException("Użytkownik nie został znaleziony");
//		}
//		return loginPerson;
//	}
//	
//	
//	public LoginPerson loadUserByUsernameAsUser(String email) throws UsernameNotFoundException {
//
//		UserDetails userDetails = loadUserByUsername(email);
//		if (userDetails instanceof LoginPerson) {
//			return (LoginPerson) userDetails;
//		} else {
//			throw new IllegalStateException("Nieprawidłowa implementacja UserDetailsService");
//		}
//	}
}
