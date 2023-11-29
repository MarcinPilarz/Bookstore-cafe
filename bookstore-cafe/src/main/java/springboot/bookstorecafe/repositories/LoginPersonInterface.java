package springboot.bookstorecafe.repositories;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface LoginPersonInterface {

	UserDetailsService userDetailsService();
}
