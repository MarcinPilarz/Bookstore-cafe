package springboot.bookstorecafe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import springboot.bookstorecafe.models.LoginPerson;
import springboot.bookstorecafe.models.RoleType;
import springboot.bookstorecafe.repositories.LoginPersonRepository;

@SpringBootApplication
public class BookstoreCafeApplication implements CommandLineRunner {

	
	@Autowired
	private LoginPersonRepository loginRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(BookstoreCafeApplication.class, args);
	}

	
	public void run(String...args) {
	
		LoginPerson adminAccount= loginRepository.findByRoleType(RoleType.Wlasciciel);
		if(null == adminAccount) {
			LoginPerson loginPerson= new LoginPerson();
			
			loginPerson.setEmail("wlasciciel@wp.pl");
			loginPerson.setRoleType(RoleType.Wlasciciel);
			loginPerson.setPassword(new BCryptPasswordEncoder().encode("admin"));
			loginRepository.save(loginPerson);
		}
	}
}
