package springboot.bookstorecafe;

import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.SecurityBuilder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import lombok.RequiredArgsConstructor;
import springboot.bookstorecafe.models.LoginPerson;
import springboot.bookstorecafe.models.RoleType;
import springboot.bookstorecafe.services.LoginPersonService;
import springboot.bookstorecafe.services.PersonService;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
	
	//private final JwtAuthenticationFilter jwtAuthenticationFilter; 
	
	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;
	
	@Autowired
	private  LoginPersonService loginPersonService;
	
//	@Autowired
	@Lazy
	//private final LoginPersonService loginPersonService;
	
//	@Autowired
//    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter, LoginPersonService loginPersonService) {
//        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
//        this.loginPersonService=loginPersonService;
//    }
	
	


//    @Autowired
//    private PasswordEncoder passwordEncoder;
	
//	@Autowired
//    private UserDetailsService userDetailsService;
//	
//	  @Bean
//	    public CorsFilter corsFilter() {
//	        CorsConfiguration config = new CorsConfiguration();
//	        config.setAllowCredentials(true);
//	        config.addAllowedOrigin("http://localhost:3000"); // URL twojej aplikacji React
//	        config.addAllowedHeader("*");
//	        config.addAllowedMethod("*");
//
//	        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//	        source.registerCorsConfiguration("/**", config); // Dla wszystkich ścieżek
//
//	        return new CorsFilter(source);
//	    }
//	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}
	
	
	 @Bean
	    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	        http
	            .cors().and() // Włączenie obsługi CORS
	            .csrf().disable() // Wyłączenie CSRF
	            .authorizeHttpRequests(auth -> auth
	                .requestMatchers("/api/v1/auth/**").permitAll()
	                .requestMatchers("/newPerson", "/images", "/person", "/loginPerson", "/deletePerson", "/newPerson", "/editPerson", "/signin","/refresh","/loginPerson", "/addOrder", "/personDetails", "/newEvent", "/orders","/reservations","/reservations/person", "/history", "/reviews/person", "/newReservation", "/newComment","/orders/person","/updateEvent", "/deleteEvent", "/customNumberOfPeople", "/cancleReservation","/coffee", "/uploadImage", "/addImage/{idProduct}", "/products", "/updateRoleType", "/order-status", "/update-order-status" ).permitAll()
	                .requestMatchers("/api/v1/admin").hasAnyAuthority(RoleType.Pracownik.name())
	                .requestMatchers("/sayAdmin","/api/v1/user", "/reviews", "/events", "/sayUser").hasAnyAuthority(RoleType.Klient.name(), RoleType.Pracownik.name(), RoleType.Wlasciciel.name())
	                .requestMatchers("/api/v1/user", "/sayAdmin", "/sayUser").hasAnyAuthority(RoleType.Wlasciciel.name())
	                .anyRequest().authenticated())
	            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
	            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

	        return http.build();
	    }

//		@Bean
//		public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//		    http.csrf(AbstractHttpConfigurer::disable)
//		        .authorizeHttpRequests(request ->
//		            request
//		                .requestMatchers("/api/v1/auth/**").permitAll()
//		                .requestMatchers("/newPerson", "/images", "/person", "/loginPerson", "/deletePerson", "/newPerson", "/editPerson", "/signin","/refresh","/loginPerson", "/events" ).permitAll()
//		                .requestMatchers("/api/v1/admin").hasAnyAuthority(RoleType.Pracownik.name())
//		                .requestMatchers("/sayAdmin","/api/v1/user", "/reviews",  "/sayUser").hasAnyAuthority(RoleType.Klient.name(), RoleType.Pracownik.name(), RoleType.Wlasciciel.name())
//		                .requestMatchers("/api/v1/user", "/sayAdmin", "/sayUser").hasAnyAuthority(RoleType.Wlasciciel.name())
//		                .anyRequest().authenticated()
//		        )
//		        .sessionManagement(manager -> manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//		        .authenticationProvider(authenticationProvider())
//		        .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
//
//		    return http.build();
//		}
	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authenticationProvider= new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(loginPersonService.userDetailsService());
		authenticationProvider.setPasswordEncoder(passwordEncoder());
		return authenticationProvider;
		
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	
    
//	@Bean
	//authentication
//	public UserDetailsService userDetailsService() {
//		UserDetails admin=User.withUsername("Marcin")
//				.password(encoder.encode("admin"))
//				.roles("ADMIN")
//				.build();
//		UserDetails user=User.withUsername("Marcin1")
//				.password(encoder.encode("admin1"))
//				.roles("USER")
//				.build();
//		
//		return new InMemoryUserDetailsManager(admin,user);
//		return new LoginPersonService();
//	}
	
//	@Bean
//  public PasswordEncoder passwordEncoder() {
//     return new BCryptPasswordEncoder();
//  }
	
//	@Bean(name = "authenticationManager")
//    public AuthenticationManager authenticationManagerBean() throws Exception {
//        return super.authenticationManagerBean();
//    }
//
//    @Autowired
//    public void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userDetailsService)
//            .passwordEncoder(passwordEncoder());
//    }
	

	
//	@Bean
//	UserDetailsService userDetailsService() {
//		InMemoryUserDetailsManager userDetailsService = new InMemoryUserDetailsManager();
//		UserDetails user = User.withUsername("javainuse").password("javainuse").authorities("read").build();
//		userDetailsService.createUser(user);
//		return userDetailsService;
//	}
//	 @Autowired
//	   private UserDetailsService userDetailsService;
//	 @Bean
//	    public PasswordEncoder passwordEncoder() {
//	        return new BCryptPasswordEncoder();
//	    }
//	
//	 
//	 protected void configure(HttpSecurity httpSecurity) throws Exception {
//		 httpSecurity
//			//.cors()
//			//.and()
//			.httpBasic()
//			.and()
//			.csrf().disable()
//			.authorizeRequests()
//			.anyRequest().permitAll()
//			.and()
//	        .logout()
//	            .logoutUrl("/logout")
//	           .permitAll();
//		 
//		 //	        http
////	        
////	        .csrf().disable()
////	            .authorizeRequests()
////	            .antMatchers("/newPerson").permitAll() // Endpoint do rejestracji dostępny dla wszystkich
////	            .anyRequest().authenticated()
////	            .and()
////	            .addFilterBefore(new JwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class); // Dodaj filtr JWT
//	    }
//
//	 @Bean
//	    public AuthenticationManager authenticationManagerBean() throws Exception {
//	        return authenticationManager();
//	    }
//	 
//	 @Autowired
//	    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//	        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
//	    }
//	 
//	 
	 
//    @Autowired
//    private LoginPersonService loginPersonService;
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Bean
//    public DaoAuthenticationProvider authenticationProvider() {
//        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
//        authProvider.setUserDetailsService(loginPersonService);
//        authProvider.setPasswordEncoder(passwordEncoder());
//        return authProvider;
//    }
//
//    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests()
//                .requestMatchers("/login").permitAll()
//                .anyRequest().authenticated()
//                .and()
//                .formLogin()
//                .loginPage("/login")
//                .defaultSuccessUrl("/home")
//                .permitAll()
//                .and()
//                .logout()
//                .logoutUrl("/logout")
//                .permitAll();
//    }
}



