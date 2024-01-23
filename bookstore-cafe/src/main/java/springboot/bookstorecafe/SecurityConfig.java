package springboot.bookstorecafe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;

import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;

import springboot.bookstorecafe.models.RoleType;
import springboot.bookstorecafe.services.LoginPersonService;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;

	@Autowired
	private LoginPersonService loginPersonService;

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.cors().and() 
				.csrf().disable() 
				.authorizeHttpRequests(auth -> auth.requestMatchers("/api/v1/auth/**").permitAll()
						.requestMatchers("/newPerson", "/images", "/person", "/loginPerson", 
								"/newPerson", "/signin", "/refresh", "/loginPerson", "/personDetails",
								 "/coffee", "/products", "/reset-password")
						.permitAll().requestMatchers("/api/v1/admin").hasAnyAuthority(RoleType.Pracownik.name())
						.requestMatchers("/sayAdmin", "/editUser", "/api/v1/user", "/reviews", "/events", "/sayUser",
								"/editPerson", "/orders/person","/newReservation","/updateRoleType", "/reservations/person")
						.hasAnyAuthority(RoleType.Klient.name(), RoleType.Pracownik.name(), RoleType.Wlasciciel.name())
						.requestMatchers("/newComment", "/deleteComment", "/history", "/reviews/person", "/addOrder")
						.hasAnyAuthority(RoleType.Klient.name())
						.requestMatchers("/newEvent", "/deleteEvent", "/customNumberOfPeople", "/cancleReservation",
								"/order-status", "/update-order-status", "/reservations", "/orders", "/newEvent",
								"/updateEvent")
						.hasAnyAuthority(RoleType.Pracownik.name(), RoleType.Wlasciciel.name())
						.requestMatchers("/uploadImage", "/updateProducts", "/deleteCoffee", "/addProduct",
								"/addImage/{idProduct}", "/employees","/deletePerson")
						.hasAnyAuthority(RoleType.Wlasciciel.name()).anyRequest().authenticated())
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}

	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(loginPersonService.userDetailsService());
		authenticationProvider.setPasswordEncoder(passwordEncoder());
		return authenticationProvider;

	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
